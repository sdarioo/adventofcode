package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public class Day18 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day18.class);

        int[][] grid = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            grid[i] = new int[line.length()];
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j) == '#' ? 1 : 0;
            }
        }
        for (int i = 0; i < 100; i++) {
            lightCorners(grid);
            grid = next(grid);
            lightCorners(grid);
        }
        System.out.println(value(grid));
    }

    private static void lightCorners(int[][] grid) {
        grid[0][0] = 1;
        grid[0][grid[0].length - 1] = 1;
        grid[grid.length - 1][0] = 1;
        grid[grid.length - 1][grid[0].length - 1] = 1;
    }

    static int[][] next(int[][] grid) {
        int[][] result = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int v = closeValue(grid, i, j);
                if (grid[i][j] == 1) {
                    result[i][j] = v == 2 || v == 3 ? 1 : 0;
                } else {
                    result[i][j] = v == 3 ? 1 : 0;
                }
            }
        }
        return result;
    }

    static int closeValue(int[][] grid, int i, int j) {
        int value = 0;
        for (int x = Math.max(0, i - 1); x <= Math.min(grid.length - 1, i + 1); x++) {
            for (int y = Math.max(0, j - 1); y <= Math.min(grid[i].length - 1, j + 1); y++) {
                if (x != i || y != j)
                    value += grid[x][y];
            }
        }
        return value;
    }

    static int value(int[][] grid) {
        int value = 0;
        for (int[] ints : grid) {
            for (int anInt : ints) {
                value += anInt;
            }
        }
        return value;
    }

}
