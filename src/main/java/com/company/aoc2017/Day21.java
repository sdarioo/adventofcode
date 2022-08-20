package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day21.class);

        var replaceMap = new HashMap<String, String>();
        lines.forEach(line -> {
            String[] split = line.split("\\s=>\\s");
            String left = split[0].replace("/", "");
            String right = split[1].replace("/", "");

            replaceMap.put(left, right);
            new Grid(left).rotations().forEach(r -> replaceMap.put(r.toString(), right));

            replaceMap.put(new Grid(left).flip1().toString(), right);
            new Grid(left).flip1().rotations().forEach(r -> replaceMap.put(r.toString(), right));
        });

        Grid grid = new Grid(".#...####");

        for (int i = 0; i < 18; i++) {
            grid = transform(grid, replaceMap);
        }

        long count = grid.toString().chars().filter(c -> c == '#').count();
        System.out.println(count);
    }


    static Grid transform(Grid grid, Map<String, String> replaceMap) {
        int size = grid.size();
        int block = size % 2 == 0 ? 2 : 3;

        var list = new ArrayList<Grid>();
        for (int y = 0; y < size; y += block) {
            for (int x = 0; x < size; x += block) {
                String sub = grid.getSubGrid(x, y, block);
                String rep = replaceMap.get(sub);
                if (rep == null) {
                    throw new IllegalArgumentException("Missing: " + sub);
                }
                list.add(new Grid(rep));
            }
        }
        return Grid.combine(list);
    }


    static class Grid {
        char[][] grid;

        Grid(String input) {
            int size = (int) Math.sqrt(input.length());
            grid = new char[size][size];
            for (int i = 0; i < input.length(); i++) {
                grid[i / size][i % size] = input.charAt(i);
            }
        }

        static Grid combine(List<Grid> grids) {
            var sb = new StringBuilder();
            int size = (int) Math.sqrt(grids.size());
            for (int y = 0; y < size; y++) {
                for (int i = 0; i < grids.get(0).size(); i++) {
                    for (int x = 0; x < size; x++) {
                        sb.append(grids.get(size * y + x).getRow(i));
                    }
                }
            }
            return new Grid(sb.toString());
        }

        int size() {
            return grid.length;
        }

        String getRow(int i) {
            return new String(grid[i]);
        }

        String getSubGrid(int x, int y, int size) {
            var sb = new StringBuilder();
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    sb.append(grid[i][j]);
                }
            }
            return sb.toString();
        }

        List<Grid> rotations() {
            var result = new ArrayList<Grid>();
            result.add(this.rotate());
            result.add(this.rotate().rotate());
            result.add(this.rotate().rotate().rotate());
            return result;
        }

        Grid rotate() {
            var sb = new StringBuilder();
            for (int x = 0; x < grid.length; x++) {
                for (int i = grid.length - 1; i >= 0; i--) {
                    sb.append(grid[i][x]);
                }
            }
            return new Grid(sb.toString());
        }

        Grid flip1() {
            var sb = new StringBuilder();
            for (int y = 0; y < grid.length; y++) {
                for (int x = grid.length - 1; x >= 0; x--) {
                    sb.append(grid[y][x]);
                }
            }
            return new Grid(sb.toString());
        }

        Grid flip2() {
            var sb = new StringBuilder();
            for (int y = grid.length - 1; y >= 0; y--) {
                for (int x = 0; x < grid.length; x++) {
                    sb.append(grid[y][x]);
                }
            }
            return new Grid(sb.toString());
        }

        @Override
        public String toString() {
            var sb = new StringBuilder();
            for (char[] row : grid) {
                sb.append(new String(row));
//                sb.append("\n");
            }
            return sb.toString();
        }
    }
}
