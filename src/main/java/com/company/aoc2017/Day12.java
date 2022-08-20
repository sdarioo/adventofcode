package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day12 {
    public static void main(String[] args) throws IOException {
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        IOUtils.getInputLines(Day12.class).stream().forEach(line -> {
            String[] split = line.split("<->");
            int left = Integer.parseInt(split[0].trim());
            List<Integer> list = Arrays.stream(split[1].split(",")).map(String::trim).map(Integer::parseInt).toList();

            graph.computeIfAbsent(left, k -> new HashSet<>()).addAll(list);
            list.forEach(v -> graph.computeIfAbsent(v, k -> new HashSet<>()).add(left));
        });

        int count = 0;
        Set<Integer> all = new HashSet<>(graph.keySet());
        while (!all.isEmpty()) {
            Integer next = all.iterator().next();
            HashSet<Integer> group = new HashSet<>();
            walk(next, graph, group);
            all.removeAll(group);
            count++;
        }

        System.out.println(count);
    }

    static void walk(int current, Map<Integer, Set<Integer>> graph, Set<Integer> visited) {
        if (visited.contains(current)) {
            return;
        }
        visited.add(current);
        graph.get(current).forEach(v -> walk(v, graph, visited));
    }
}
