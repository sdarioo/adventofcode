package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class Day3 {
    public static void main(String[] args) throws IOException {

        List<String> lines = IOUtils.getInputLines(Day3.class);

        String[] path1 = lines.get(0).split(",");
        String[] path2 = lines.get(1).split(",");

        Point current = new Point(0, 0);
        List<Point> p1 = new ArrayList<>();
        for (String inst : path1) {
            move(inst, current, p1);
            current = p1.get(p1.size() - 1);
        }

        current = new Point(0, 0);
        List<Point> p2 = new ArrayList<>();
        for (String inst : path2) {
            move(inst, current, p2);
            current = p2.get(p2.size() - 1);
        }

        HashSet<Point> common = new HashSet<>(p1);
        common.retainAll(new HashSet<>(p2));

        int result = Integer.MAX_VALUE;
        for (Point point : common) {
            result = Math.min(result, 2 + p1.indexOf(point) + p2.indexOf(point));
        }
        System.out.println(result);


//        int result = common.stream().mapToInt(p -> Math.abs(p.x()) + Math.abs(p.y())).min().orElse(-1);
//        System.out.println(result);
    }

    static void move(String inst, Point current, List<Point> points) {
        char d = inst.charAt(0);
        int distance = Integer.parseInt(inst.substring(1));
        switch (d) {
            case 'U' ->
                    IntStream.range(1, distance + 1).forEach(y -> points.add(new Point(current.x(), current.y() + y)));
            case 'D' ->
                    IntStream.range(1, distance + 1).forEach(y -> points.add(new Point(current.x(), current.y() - y)));
            case 'L' ->
                    IntStream.range(1, distance + 1).forEach(x -> points.add(new Point(current.x() - x, current.y())));
            case 'R' ->
                    IntStream.range(1, distance + 1).forEach(x -> points.add(new Point(current.x() + x, current.y())));
            default -> throw new IllegalArgumentException("Illegal instruction: " + inst);
        }
    }

    record Point(int x, int y) {
    }
}
