package com.company.aoc2017;

public class Day14 {

    public static void main(String[] args) {
        String prefix = "ugkiagan";

        int[][] grid = new int[128][];
        for (int i = 0; i <= 127; i++) {
            String input = prefix + '-' + i;
            String hash = Day10.knotHash(input);
            var sb = new StringBuilder();
            for (char c : hash.toCharArray()) {
                String bin = Integer.toBinaryString(Integer.parseInt("" + c, 16));
                sb.append("0".repeat(4 - bin.length())).append(bin);
            }
            grid[i] = sb.toString().chars().map(n -> n - 48).toArray();
        }
        System.out.println(regions(grid));
    }

    static int regions(int[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    clearRegion(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    static void clearRegion(int[][] grid, int x, int y) {
        if (x < 0) return;
        if (y < 0) return;
        if (x == grid.length) return;
        if (y == grid.length) return;
        if (grid[x][y] == 1) {
            grid[x][y] = 0;
            clearRegion(grid, x - 1, y);
            clearRegion(grid, x, y - 1);
            clearRegion(grid, x + 1, y);
            clearRegion(grid, x, y + 1);
        }
    }

}
