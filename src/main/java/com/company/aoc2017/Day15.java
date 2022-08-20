package com.company.aoc2017;

import lombok.AllArgsConstructor;

public class Day15 {

    public static void main(String[] args) {
        Generator a = new Generator(512, 16807, 4);
        Generator b = new Generator(191, 48271, 8);

        int count = 0;
        for (int i = 0; i < 5_000_000; i++) {
            a.genNext();
            b.genNext();

            if ((a.value & 0xFFFF) == (b.value & 0xFFFF)) {
                count++;
            }
        }
        System.out.println(count);
    }

    @AllArgsConstructor
    static class Generator {
        long value;
        final int factor;
        final int div;

        void genNext() {
            while (true) {
                value = (value * factor) % 2147483647L;
                if (value % div == 0) {
                    break;
                }
            }
        }
    }
}
