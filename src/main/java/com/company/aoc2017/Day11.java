package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;

public class Day11 {

    public static void main(String[] args) throws IOException {
        String input = IOUtils.getInputAsString(Day11.class);
        //input = "se,sw,se,sw,sw";
        String[] split = input.split(",");

        int x = 0;
        int y = 0;
        int max = 0;
        for (String s : split) {
            if ("n".equals(s)) {
                y += 2;
            } else if ("s".equals(s)) {
                y -= 2;
            } else if ("nw".equals(s)) {
                y++;
                x--;
            } else if ("ne".equals(s)) {
                y++;
                x++;
            } else if ("sw".equals(s)) {
                y--;
                x--;
            } else if ("se".equals(s)) {
                y--;
                x++;
            } else {
                throw new IllegalArgumentException("Invalid s: " + s);
            }
            max = Math.max(max, distance(x, y));
        }

        System.out.println("x=" + x + ", y=" + y);
        System.out.println("Max: " + max);
        System.out.println(distance(x, y));
    }

    static int distance(int x, int y) {
        int result = Math.abs(x);
        if (Math.abs(y) > Math.abs(x)) {
            result += (Math.abs(y) - Math.abs(x)) / 2;
        }
        return result;
    }

}
