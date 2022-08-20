package com.company.aoc2017;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) {
        String input = "5 1 10 0 1 7 13 14 3 12 8 10 7 12 0 6";
//        String input = "0 2 7 0";
        int[] banks = Arrays.stream(input.split("\\s")).mapToInt(Integer::parseInt).toArray();

        String duplicated;
        int cycles = 1;
        Set<String> unique = new LinkedHashSet<>();
        while (true) {
            // Find max index
            int index = 0;
            for (int i = 1; i < banks.length; i++) {
                if (banks[i] > banks[index]) index = i;
            }
            // Redistribute
            int start = index + 1;
            int count = banks[index];
            banks[index] = 0;
            for (int i = start; i < start + count; i++) {
                banks[i % banks.length]++;
            }

            // Check if unique
            String str = Arrays.stream(banks).mapToObj(String::valueOf).collect(Collectors.joining(","));
            if (unique.contains(str)) {
                duplicated = str;
                break;
            }

            unique.add(str);
            cycles++;
        }

        int pos = 0;
        Iterator<String> it = unique.iterator();
        while (it.hasNext() && !duplicated.equals(it.next())) {
            pos++;
        }

        System.out.println(unique.size() - pos);
    }

}
