package com.company.aoc2019;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;

public class Day4 {
    public static void main(String[] args) {

        int min = 382345;
        int max = 843167;

        System.out.println(new Scanner(min, max).scan());
    }

    @RequiredArgsConstructor
    static class Scanner {
        final int min;
        final int max;

        int count;

        int scan() {
            scan(0, 1, 100000, new ArrayDeque<>());
            return count;
        }

        private void scan(int value, int startIdx, int base, Deque<Integer> groups) {
            for (int i = startIdx; i <= 9; i++) {
                if (i == startIdx) {
                    groups.addLast(i);
                }
                int nextValue = value + base * i;
                int nextBase = base / 10;
                if (nextBase > 0) {
                    scan(nextValue, i, nextBase, groups);
                } else if (nextValue >= min && nextValue <= max && !groups.isEmpty()) {
                    String nextValueStr = String.valueOf(nextValue);
                    if (groups.stream().anyMatch(n -> !nextValueStr.contains(String.valueOf(n).repeat(3)))) {
                        count++;
                    }
                }
                if (i == startIdx) {
                    groups.removeLast();
                }
            }
        }
    }
}
