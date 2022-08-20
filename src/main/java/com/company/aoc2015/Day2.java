package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day2 {
    public static void main(String[] args) throws IOException {

        try (Stream<String> lines = IOUtils.getInputLines(Day2.class).stream()) {
        //try (Stream<String> lines = Arrays.asList("2x3x4").stream()) {
            final int total = lines
                    .map(l -> Arrays.stream(l.split("x")).mapToInt(Integer::parseInt).toArray())
                    .mapToInt(a -> min(2 * a[0] + 2 * a[1], 2 * a[0] + 2 * a[2], 2 * a[1] + 2 * a[2]) + a[0] * a[1] * a[2])
                    .sum();

            System.out.println(total);
        }

    }

    static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}
