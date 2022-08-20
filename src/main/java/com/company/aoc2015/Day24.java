package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;

public class Day24 {
    public static void main(String[] args) throws IOException {
        int[] packages = IOUtils.getInputLines(Day24.class).stream().mapToInt(Integer::parseInt).toArray();

        int totalWeight = Arrays.stream(packages).sum();
        int groupWeight = totalWeight / 3;

        for (int i = 1; i < packages.length; i++) {
            long qe = findGroupQE(packages, i, 0, groupWeight);
            if (qe < Long.MAX_VALUE) {
                System.out.println("Found group: " + i + " with qe=" + qe);
                break;
            }
        }
    }

    static long findGroupQE(int[] packages, int count, int startIdx, int expectedWeight) {
        if (count == 0) {
            return expectedWeight == 0 ? 1 : Long.MAX_VALUE;
        }

        long minQE = Long.MAX_VALUE;
        for (int i = startIdx; i < packages.length; i++) {
            long qe = findGroupQE(packages, count - 1, i + 1, expectedWeight - packages[i]);
            if (qe < Long.MAX_VALUE) {
                minQE = Math.min(packages[i] * qe, minQE);
            }
        }
        return minQE;
    }
}
