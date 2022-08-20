package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.function.IntUnaryOperator;

public class Day6 {

    public static void main(String[] args) throws IOException {
        var lines = IOUtils.getInputLines(Day6.class);

        int[][] grid = new int[1_000][1_000];
        lines.forEach(line -> {
            int[] region;
            IntUnaryOperator func;
            if (line.startsWith("toggle")) {
                region = getRegion(line.substring(6));
                func = value -> value + 2;
            } else if (line.startsWith("turn on")) {
                region = getRegion(line.substring(7));
                func = value -> value + 1;
            } else if (line.startsWith("turn off")) {
                region = getRegion(line.substring(8));
                func = value -> value > 0 ? value - 1 : 0;
            } else {
                throw new IllegalArgumentException("Invalid line: " + line);
            }
            for (int i = region[0]; i <= region[2]; i++) {
                for (int j = region[1]; j <= region[3]; j++) {
                    grid[i][j] = func.applyAsInt(grid[i][j]);
                }
            }
        });
        int total = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                total += grid[i][j];
            }
        }
        System.out.println(total);
    }

    static int[] getRegion(String text) {
        String[] split = text.trim().split("[\\s|,]", -1);
        return new int[]{
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[3]),
                Integer.parseInt(split[4])};
    }

}
