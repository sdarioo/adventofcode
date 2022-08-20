package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14 {

    public static void main(String[] args) throws IOException {
        List<Reaction> reactions = IOUtils.getInputLines(Day14.class).stream()
                .map(Reaction::of)
                .toList();

        Map<String, Reaction> reactionMap = reactions.stream().collect(Collectors.toMap(r -> r.output.name, r -> r));
        FuelCost fuelCost = getFuelCost(reactionMap);
        System.out.println("1 FUEL = " + fuelCost.ore + " ORE");

        System.out.println(getMaxFuel(1000000000000L, new HashMap<>(), fuelCost, reactionMap));
    }

    static long getMaxFuel(long ore, Map<String, Long> input, FuelCost fuelCost, Map<String, Reaction> reactionMap) {
        long fuel = ore / fuelCost.ore;
        if (fuel == 0) {
            return 0;
        }
        ore = ore % fuelCost.ore;

        input = add(input, multiply(fuelCost.extra, fuel));

        var basicInput = new HashMap<String, Long>();
        input.forEach((k, v) -> {
            Reaction reaction = findBasicReaction(new Chemical(k, v), reactionMap);
            Arrays.stream(reaction.input).forEach(ch ->
                    basicInput.put(ch.name, basicInput.getOrDefault(ch.name, 0L) + ch.count));
        });

        ore += basicInput.getOrDefault("ORE", 0L);
        basicInput.remove("ORE");

        return fuel + getMaxFuel(ore, basicInput, fuelCost, reactionMap);
    }

    static Map<String, Long> multiply(Map<String, Long> input, long value) {
        return input.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() * value));
    }

    static Map<String, Long> add(Map<String, Long> map1, Map<String, Long> map2) {
        var result = new HashMap<>(map1);
        map2.forEach((k, v) -> result.put(k, result.getOrDefault(k, 0L) + v));
        return result;
    }

    static Reaction findBasicReaction(Chemical chemical, Map<String, Reaction> reactionMap) {
        var result = new Reaction(new Chemical[]{chemical}, chemical);

        while (true) {
            boolean changed = false;
            var input = new HashMap<String, LongAdder>();
            for (Chemical in : result.input) {
                Reaction reaction = reactionMap.get(in.name());
                if (reaction == null || reaction.output.count() > in.count()) {
                    input.computeIfAbsent(in.name, k -> new LongAdder()).add(in.count());
                    continue;
                }
                changed = true;
                long times = in.count() / reaction.output.count();
                long remainder = in.count() % reaction.output.count();
                if (remainder > 0) {
                    input.computeIfAbsent(in.name(), k -> new LongAdder()).add(remainder);
                }
                for (Chemical ch : reaction.input()) {
                    input.computeIfAbsent(ch.name(), k -> new LongAdder()).add(times * ch.count());
                }
            }
            if (!changed) {
                break;
            }
            result = new Reaction(input.entrySet().stream()
                    .map(e -> new Chemical(e.getKey(), e.getValue().longValue()))
                    .toArray(Chemical[]::new), chemical);
        }
        return result;
    }

    static FuelCost getFuelCost(Map<String, Reaction> reactionMap) {
        var extra = new HashMap<String, Long>();
        long ore = getCost(new Chemical("FUEL", 1), reactionMap, extra);
        return new FuelCost(ore, extra);
    }

    static int getCost(Chemical output, Map<String, Reaction> reactionMap, Map<String, Long> extra) {
        Long extraCount = extra.get(output.name);
        if (extraCount != null && extraCount >= output.count) {
            extra.put(output.name, extraCount - output.count);
            return 0;
        }
        if (extraCount != null) {
            extra.remove(output.name);
            output = new Chemical(output.name, output.count - extraCount);
        }

        Reaction reaction = reactionMap.get(output.name);
        int count = output.count <= reaction.output.count ? 1
                : (int) Math.ceil((double) output.count / reaction.output.count);

        int cost = 0;
        for (int i = 0; i < count; i++) {
            for (Chemical ch : reaction.input) {
                cost += "ORE".equals(ch.name) ? ch.count : getCost(ch, reactionMap, extra);
            }
        }

        if (count * reaction.output.count > output.count) {
            extra.put(output.name,
                    extra.getOrDefault(output.name, 0L) + count * reaction.output.count - output.count);
        }

        return cost;
    }

    record FuelCost(long ore, Map<String, Long> extra) {
    }

    record Chemical(String name, long count) {
        @Override
        public String toString() {
            return count + " " + name;
        }
    }

    record Reaction2(Chemical out, Map<String, Long> input) {
        Reaction2(Chemical out, Chemical... in) {
            this(out, new HashMap<>());
            for (Chemical chemical : in) {
                input.put(chemical.name, chemical.count);
            }
        }

        void addInput(String name, Long count) {
            Long prev = input.get(name);
            input.put(name, count + (prev != null ? prev : 0L));
        }
    }

    record Reaction(Chemical[] input, Chemical output) {
        public static Reaction of(String line) {
            int index = line.indexOf("=>");
            Function<String, Chemical[]> parser = str -> Arrays.stream(str.split(","))
                    .map(String::trim)
                    .map(s -> s.split("\\s"))
                    .map(arr -> new Chemical(arr[1], Integer.parseInt(arr[0])))
                    .toArray(Chemical[]::new);
            return new Reaction(parser.apply(line.substring(0, index)),
                    parser.apply(line.substring(index + 2))[0]);
        }

        Optional<Chemical> findInput(String name) {
            return Arrays.stream(input).filter(in -> in.name.equals(name)).findFirst();
        }

        @Override
        public String toString() {
            return Arrays.stream(input).map(String::valueOf).collect(Collectors.joining(", ")) +
                    " => " +
                    output;
        }
    }
}
