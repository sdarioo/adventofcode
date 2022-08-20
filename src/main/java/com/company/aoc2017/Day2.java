package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;

public class Day2 {
    public static void main(String[] args) throws IOException {
        int sum = IOUtils.getInputLines(Day2.class).stream()
                .map(line -> line.split("\\s"))
                .map(strArray -> Arrays.stream(strArray).mapToInt(Integer::parseInt).toArray())
                //.mapToInt(intArray -> IntStream.of(intArray).max().orElse(-1) - IntStream.of(intArray).min().orElse(-1))
                .mapToInt(Day2::div)
                .sum();

        System.out.println(sum);
    }

    static int div(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int x = array[i];
            for (int j = i + 1; j < array.length; j++) {
                int y = array[j];
                if ((x > y && x % y == 0) || (y > x && y % x == 0)) {
                    return x > y ? x / y : y / x;
                }
            }
        }
        throw new IllegalArgumentException("No div found!");
    }
}
