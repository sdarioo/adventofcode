package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day6.class);

        // Keys represents objects orbiting Value
        Map<String, String> orbitMap = lines.stream()
                .map(line -> line.split("\\)"))
                .collect(Collectors.toMap(a -> a[1], a -> a[0]));

        // Keys represent objects being orbited by multiple Values
        Map<String, List<String>> orbitingMap = orbitMap.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        HashSet<String> visited = new HashSet<>();
        path(orbitMap.get("YOU"), orbitMap.get("SAN"), new Space(orbitMap, orbitingMap), visited);
        System.out.println(visited.size());
    }

    static boolean path(String from, String to, Space space, Set<String> visited) {
        if (from.equals(to)) {
            return true;
        }
        boolean found = false;
        Set<String> neighbours = space.getNeighbours(from);
        for (String obj : neighbours) {
            if (visited.contains(obj)) {
                continue;
            }
            visited.add(obj);
            if (path(obj, to, space, visited)) {
                found = true;
                break;
            }
            visited.remove(obj);
        }
        return found;
    }

    record Space(Map<String, String> orbitMap, Map<String, List<String>> orbitingMap) {

        Set<String> getNeighbours(String obj) {
            var list = new HashSet<>(orbitingMap.getOrDefault(obj, Collections.emptyList()));
            if (orbitMap.containsKey(obj)) {
                list.add(orbitMap.get(obj));
            }
            return list;
        }
    }

    static int orbitsCount(String object, Map<String, String> orbitMap) {
        String other = orbitMap.get(object);
        return other != null ? 1 + orbitsCount(other, orbitMap) : 0;
    }

}
