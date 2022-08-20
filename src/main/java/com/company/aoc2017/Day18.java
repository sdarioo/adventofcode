package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

public class Day18 {

    public static void main(String[] args) throws IOException {
        List<String> list = IOUtils.getInputLines(Day18.class);

        Program p0 = new Program(0, list);
        Program p1 = new Program(1, list);
        p0.other = p1;
        p1.other = p0;

        while (p0.isRunning() || p1.isRunning()) {
            p0.step();
            p1.step();
        }
        System.out.println(p1.sendCount);
    }

    static class Program {
        final List<String> list;

        final Queue<Long> queue = new ArrayDeque<>();
        final Map<String, Long> reg = new HashMap<>();

        int index;
        int sendCount;
        boolean deadlock;
        Program other;

        Program(long id, List<String> list) {
            this.list = list;
            this.reg.put("p", id);
        }

        void send(long value) {
            other.queue.offer(value);
            sendCount++;
        }

        void step() {
            if (index < 0 || index >= list.size()) {
                return;
            }
            String[] inst = list.get(index).split("\\s");
            try {
                switch (inst[0]) {
                    case "set" -> reg.put(inst[1], get(reg, inst[2]));
                    case "add" -> reg.put(inst[1], get(reg, inst[1]) + get(reg, inst[2]));
                    case "mul" -> reg.put(inst[1], get(reg, inst[1]) * get(reg, inst[2]));
                    case "mod" -> reg.put(inst[1], get(reg, inst[1]) % get(reg, inst[2]));
                    case "jgz" -> index += get(reg, inst[1]) > 0 ? get(reg, inst[2]) - 1 : 0;
                    case "snd" -> send(get(reg, inst[1]));
                    case "rcv" -> reg.put(inst[1], queue.remove());
                    default -> throw new IllegalArgumentException("Unknown instruction: " + inst[0]);
                }
                index++;
                deadlock = false;
            } catch (NoSuchElementException e) {
                deadlock = true;
            }
        }

        boolean isRunning() {
            return index >= 0 && index < list.size() && !deadlock;
        }
    }

    static long get(Map<String, Long> reg, String value) {
        return toInt(value.trim()).orElseGet(() -> reg.getOrDefault(value.trim(), 0L));
    }

    static Optional<Long> toInt(String str) {
        try {
            return Optional.of(Long.parseLong(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }


}
