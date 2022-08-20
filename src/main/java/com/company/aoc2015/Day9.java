package com.company.aoc2015;

import com.company.utils.IOUtils;
import lombok.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {

    public static void main(String[] args) throws IOException {
        String input = IOUtils.getInputAsString(Day9.class);

        List<Vertex> vList = Files.readAllLines(Paths.get(input)).stream()
                .map(l -> l.split("\\s"))
                .map(l -> new Vertex(l[0], l[2], Integer.parseInt(l[4])))
                .collect(Collectors.toList());

        Map<String, List<Vertex>> paths = new HashMap<>();
        vList.forEach(v -> paths.computeIfAbsent(v.getFrom(), k -> new ArrayList<>()).add(v));
        vList.forEach(v -> paths.computeIfAbsent(v.getTo(), k -> new ArrayList<>()).add(v.reversed()));

        Set<String> cities = vList.stream()
                .flatMap(v -> Stream.of(v.from, v.to))
                .collect(Collectors.toSet());

        int result = cities.stream()
                .mapToInt(city -> getLongest(city, paths, 0, new HashSet<>(), cities.size()))
                .max()
                .orElse(-1);

        System.out.println(result);
    }

    private static int getLongest(String city, Map<String, List<Vertex>> paths, int total, Set<String> visited, int citiesCount) {
        visited.add(city);
        if (visited.size() == citiesCount) {
            return total;
        }

        return paths.get(city).stream()
                .filter(v -> !visited.contains(v.to))
                .mapToInt(v -> getLongest(v.to, paths, total + v.distance, new HashSet<>(visited), citiesCount))
                .max()
                .orElse(Integer.MIN_VALUE);
    }

    @Value
    static class Vertex {
        String from;
        String to;
        int distance;

        Vertex reversed() {
            return new Vertex(to, from, distance);
        }
    }

}
