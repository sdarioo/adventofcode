package com.company.aoc2015;

import java.util.Arrays;

public class Day20 {

    public static void main(String[] args) {
        int total = 34000000;
        int[] houses = new int[total];
        Arrays.fill(houses, 11);

        for (int i = 2; i <= total; i++) {
            int j = i - 1;
            int count = 0;
            while (j < houses.length) {
                houses[j] += (i * 11);
                if (count++ == 50) break;
                j += i;
            }
        }

        for (int i = 0; i < houses.length; i++) {
            if (houses[i] >= total) {
                System.out.println("Found: " + (i + 1));
                break;
            }
        }
        System.out.println("done");
        for (int i = 0; i < 9; i++) {
            System.out.println("House: " + (i + 1) + " got " + houses[i] + "  presents");
        }
    }

}
