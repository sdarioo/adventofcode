package com.company.aoc2015;

import java.util.Arrays;
import java.util.List;

public class Day15 {
    static String input = "Sprinkles: capacity 5, durability -1, flavor 0, texture 0, calories 5\n" +
            "PeanutButter: capacity -1, durability 3, flavor 0, texture 0, calories 1\n" +
            "Frosting: capacity 0, durability -1, flavor 4, texture 0, calories 6\n" +
            "Sugar: capacity -1, durability 0, flavor 0, texture 2, calories 8";

    public static void main(String[] args) {
        List<Ingredient> ingredients = Arrays.stream(input.split("\\n"))
                .map(l -> l.split("\\s"))
                .map(a -> new Ingredient(a[0], toInt(a[2]), toInt(a[4]), toInt(a[6]), toInt(a[8]), toInt(a[10])))
                .toList();

        long max = 0;
        for (int a = 0; a <= 100; a++) {
            for (int b = 0; b <= (100 - a); b++) {
                for (int c = 0; c <= (100 - a - b); c++) {
                    for (int d = 0; d <= (100 - a - b - c); d++) {
                        if (a + b + c + d != 100) {
                            continue;
                        }
                        if (calories(ingredients, a, b, c, d) == 500) {
                            max = Math.max(max, score(ingredients, a, b, c, d));
                        }
                    }
                }
            }
        }
        System.out.println(max);
    }

    static long calories(List<Ingredient> list, long a, long b, long c, long d) {
        return a * list.get(0).calories() + b * list.get(1).calories() + c * list.get(2).calories() + d * list.get(3).calories();
    }

    static long score(List<Ingredient> list, long a, long b, long c, long d) {
        long cap = a * list.get(0).capacity() + b * list.get(1).capacity() + c * list.get(2).capacity() + d * list.get(3).capacity();
        long dur = a * list.get(0).durability() + b * list.get(1).durability() + c * list.get(2).durability() + d * list.get(3).durability();
        long fla = a * list.get(0).flavor() + b * list.get(1).flavor() + c * list.get(2).flavor() + d * list.get(3).flavor();
        long tex = a * list.get(0).texture() + b * list.get(1).texture() + c * list.get(2).texture() + d * list.get(3).texture();
        return pos(cap) * pos(dur) * pos(fla) * pos(tex);
    }

    static long pos(long v) {
        return v > 0 ? v : 0;
    }

    static int toInt(String v) {
        return Integer.parseInt(v.replace(",", ""));
    }

    private record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
    }
}
