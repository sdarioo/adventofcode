package com.company.aoc2015;

public class Day17 {
    public static void main(String[] args) {
        int sum = 150;
        int[] values = {11, 30, 47, 31, 32, 36, 3, 1, 5, 3, 32, 36, 15, 11, 46, 26, 28, 1, 19, 3};
        //int[] values = {20, 15, 10, 5, 5};

        Result result = new Result();
        combinations(values, 0, 150, 1, result);
        System.out.println(result.count);
    }

    static void combinations(int[] values, int index, int sum, int depth, Result result) {
        if (index == values.length) {
            return;
        }

        for (int i = index; i < values.length; i++) {
            int value = values[i];
            if (value == sum) {
                if (depth < result.min) {
                    result.min = depth;
                    result.count = 1;
                } else if (depth == result.min) {
                    result.count++;
                }
            } else if (value < sum) {
                combinations(values, i + 1, sum - value, depth + 1, result);
            }
        }
    }

    static class Result {
        int min = Integer.MAX_VALUE;
        int count;
    }

}
