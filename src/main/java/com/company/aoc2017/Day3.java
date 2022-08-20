package com.company.aoc2017;

import java.util.Arrays;

public class Day3 {
    public static void main(String[] args) {
        int input = 312051;

        System.out.println(part2(input));
        //System.out.println(part1(input));
    }

    static int part2(int input) {
        int[][] g = new int[100][100];
        int x = 50;
        int y = 50;
        g[x][y] = 1;

        for (int i = 1; i < 20; i++) {
            x++;
            if (set(g, x, y) > input) return set(g, x, y);
            int d = (i * 8) / 4;
            // move up
            System.out.println("up");
            for (int j = 0; j < d - 1; j++) {
                y--;
                if (set(g, x, y) > input) return set(g, x, y);
            }
            // move left
            System.out.println("left");
            for (int j = 0; j < d; j++) {
                x--;
                if (set(g, x, y) > input) return set(g, x, y);
            }
            // move down
            System.out.println("down");
            for (int j = 0; j < d; j++) {
                y++;
                if (set(g, x, y) > input) return set(g, x, y);
            }
            // move right
            System.out.println("right");
            for (int j = 0; j < d; j++) {
                x++;
                if (set(g, x, y) > input) return set(g, x, y);
            }
        }
        throw new IllegalArgumentException();
    }

    static int set(int[][] g, int x, int y) {
        g[x][y] = g[x - 1][y] + g[x + 1][y] + g[x][y + 1] + g[x][y - 1] + g[x - 1][y - 1] + g[x + 1][y + 1] + g[x - 1][y + 1] + g[x + 1][y - 1];
        System.out.println(String.format("(%d,%d) -> %d", x, y, g[x][y]));
        return g[x][y];
    }

    static int part1(int input) {
        int i = 1;
        while (true) {
            int max = 8 * ((1 + i) * i) / 2 + 1;
            int min = max - (8 * i) + 1;
            if (input >= min && input <= max) {
                int a = min + (i - 1);
                int b = a + (i * 8) / 4;
                int c = b + (i * 8) / 4;
                int d = c + (i * 8) / 4;
                return i + Arrays.asList(
                                Math.abs(input - a),
                                Math.abs(input - b),
                                Math.abs(input - c),
                                Math.abs(input - d)).stream()
                        .mapToInt(Integer::valueOf).min().orElseThrow();
            }
            i++;
        }
    }
}
