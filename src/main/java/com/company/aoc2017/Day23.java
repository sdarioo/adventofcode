package com.company.aoc2017;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Day23 {

    public static void main(String[] args) throws IOException {
        System.out.println(run());
    }

    static long run() {
        long a = 1;
        long b = 93;
        long c = 93;
        long d = 0;
        long e = 0;
        long f = 0;
        long g = 0;
        long h = 0;

        if (a != 0) {
            b *= 100;
            b += 100_000;
            c = b + 17_000;
        }
        while (true) {
            f = 1;

            for (d = 2; d < b; d++) {
                for (e = 2; e < b; e++) {
                    long de = d * e;
                    if (de >= b) {
                        f = de == b ? 0 : 1;
                        break;
                    }
                }
                if (f == 0) break;
            }

            if (f == 0) {
                h++;
            }
            if (b == c) {
                break;
            }
            b += 17;
        }
        System.out.println("a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e + ", f=" + f + ", g=" + g + ", h=" + h);
        return h;
    }

    static long run1() {
        long a = 0;
        long b = 93;
        long c = 93;
        long d = 0;
        long e = 0;
        long f = 0;
        long g = 0;
        long h = 0;

        if (a != 0) {
            b *= 100;
            b += 100_000;
            c = b + 17_000;
        }
        while (true) {
            f = 1;
            d = 2;
            do {
                e = 2;
                do {
                    g = d * e - b;
                    if (g == 0) {
                        f = 0;
                    }
                    e++;
                    g = e - b;
                } while (g != 0);
                d++;
                g = d - b;
            } while (g != 0);

            if (f == 0) {
                h++;
            }
            g = b - c;

            if (g == 0) {
                break;
            }
            b += 17;
        }
        System.out.println("a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e + ", f=" + f + ", g=" + g + ", h=" + h);
        return h;
    }

    static void run2(List<String> lines) {
        var reg = new HashMap<String, Long>();
        //reg.put("a", 1L);

        int index = 0;
        while (index >= 0 && index < lines.size()) {
            String[] inst = lines.get(index).split("\\s");
            switch (inst[0]) {
                case "set" -> reg.put(inst[1], get(reg, inst[2]));
                case "sub" -> reg.put(inst[1], get(reg, inst[1]) - get(reg, inst[2]));
                case "mul" -> reg.put(inst[1], get(reg, inst[1]) * get(reg, inst[2]));
                case "jnz" -> index += get(reg, inst[1]) != 0 ? get(reg, inst[2]) - 1 : 0;
                default -> throw new IllegalArgumentException("Unknown instruction: " + inst[0]);
            }
            index++;
        }
        System.out.println("reg=" + reg);
        System.out.println(reg.get("h"));
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
