package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {

    public static void main(String[] args) throws IOException {
        try (Stream<String> lines = IOUtils.getInputLines(Day13.class).stream()) {
            Map<String, Map<String, Integer>> map = lines.map(l -> l.split("\\s"))
                    .collect(Collectors.groupingBy(a -> a[0],
                            Collectors.toMap(a -> new Neighbour(a).name(), a -> new Neighbour(a).value())));

            List<String> people = new ArrayList<>(map.keySet());
            people.forEach(person -> {
                map.computeIfAbsent("me", k -> new HashMap<>()).put(person, 0);
                map.get(person).put("me", 0);
            });

            List<LinkedList<String>> permutation = find(map.keySet());
            System.out.println(permutation.stream().mapToInt(t -> happiness(t, map)).max());
        }
    }

    static List<LinkedList<String>> find(Set<String> people) {
        if (people.isEmpty()) {
            return Collections.emptyList();
        }
        List<LinkedList<String>> result = new ArrayList<>();
        people.forEach(person -> {
            Set<String> other = new HashSet<>(people);
            other.remove(person);
            result.addAll(zip(person, find(other)));
        });
        return result;
    }

    static List<LinkedList<String>> zip(String person, List<LinkedList<String>> list) {
        return list.isEmpty()
                ? List.of(new LinkedList<>(List.of(person)))
                : list.stream().peek(l -> l.addFirst(person)).toList();
    }


    static int happiness(List<String> table, Map<String, Map<String, Integer>> map) {
        int count = 0;
        for (int i = 0; i < table.size(); i++) {
            String prev = i == 0 ? table.get(table.size() - 1) : table.get(i - 1);
            String next = i == table.size() - 1 ? table.get(0) : table.get(i + 1);
            count += map.get(table.get(i)).get(prev);
            count += map.get(table.get(i)).get(next);
        }
        return count;
    }

    record Neighbour(String name, int value) {
        Neighbour(String[] array) {
            this(array[10].replace('.', ' ').trim(),
                    Integer.parseInt(array[3]) * ("lose".equals(array[2]) ? -1 : 1));
        }
    }
}
