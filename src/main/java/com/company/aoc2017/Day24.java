package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day24 {

    public static void main(String[] args) throws IOException {

        List<Port> ports = IOUtils.getInputLines(Day24.class).stream()
                .map(line -> line.split("/"))
                .map(pair -> new Port(Integer.parseInt(pair[0]), Integer.parseInt(pair[1])))
                .toList();

        Map<Integer, List<Port>> portMap = new HashMap<>();
        ports.forEach(p -> {
            portMap.computeIfAbsent(p.pin1, k -> new ArrayList<>()).add(p);
            if (p.pin1 != p.pin2) {
                portMap.computeIfAbsent(p.pin2, k -> new ArrayList<>()).add(p);
            }
        });

        System.out.println("START...");
        System.out.println(search(portMap, new Bridge()));
        // 306 - bad result :(
    }

    static Result search(Map<Integer, List<Port>> portMap, Bridge bridge) {
        int type = bridge.type();

        Result result = new Result(bridge.size(), bridge.strength());
        List<Port> matching = portMap.computeIfAbsent(type, k -> Collections.emptyList());

        for (Port port : matching) {
            if (!bridge.add(port)) {
                continue;
            }
            Result other = search(portMap, bridge);
            if (other.compareTo(result) > 0) {
                result = other;
            }

            bridge.removeLast();
        }
        return result;
    }

    record Port(int pin1, int pin2) {
        @Override
        public String toString() {
            return pin1 + "/" + pin2;
        }
    }

    record Result(int len, int strength) implements Comparable<Result> {

        @Override
        public int compareTo(Result other) {
            return len < other.len
                    ? -1
                    : len == other.len ? Integer.compare(strength, other.strength) : 1;
        }
    }

    static class Bridge {
        final Deque<Port> ports = new ArrayDeque<>();
        final Deque<Integer> types = new ArrayDeque<>();
        final Set<Port> portsSet = new HashSet<>();

        int strength() {
            return ports.stream().mapToInt(p -> p.pin1 + p.pin2).sum();
        }

        int size() {
            return ports.size();
        }

        boolean add(Port port) {
            if (portsSet.add(port)) {
                ports.addLast(port);
                types.addLast(port.pin1() == type() ? port.pin2() : port.pin1());
                return true;
            }
            return false;
        }

        void removeLast() {
            types.removeLast();
            Port last = ports.removeLast();
            portsSet.remove(last);
        }

        int type() {
            return types.isEmpty() ? 0 : types.getLast();
        }

        @Override
        public String toString() {
            return ports.toString();
        }
    }
}
