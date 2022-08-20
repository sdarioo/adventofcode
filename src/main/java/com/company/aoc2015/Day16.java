package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Day16 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day16.class);

        var message = getMessage();

        Map<String, Integer>[] maps = lines.stream()
                .map(l -> l.split("[\\s,:]"))
                .map(Day16::toMap)
                .toArray(Map[]::new);

        for (int i = 0; i < maps.length; i++) {
            if (maps[i].entrySet().stream().allMatch(e -> matches(e.getKey(), e.getValue(), message.get(e.getKey())))) {
                System.out.println(i + 1);
            }
        }
    }

    static boolean matches(String key, int value, int real) {
        if ("cats".equals(key) || "trees".equals(key)) {
            return real < value;
        }
        if ("pomeranians".equals(key) || "goldfish".equals(key)) {
            return real > value;
        }
        return real == value;
    }

    static Map<String, Integer> toMap(String[] line) {
        return Map.of(line[3], Integer.parseInt(line[5]),
                line[7], Integer.parseInt(line[9]),
                line[11], Integer.parseInt(line[13]));
    }

    static Map<String, Integer> getMessage() {
        return Map.of("children", 3,
                "cats", 7,
                "samoyeds", 2,
                "pomeranians", 3,
                "akitas", 0,
                "vizslas", 0,
                "goldfish", 5,
                "trees", 3,
                "cars", 2,
                "perfumes", 1);
    }

}
