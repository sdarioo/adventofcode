package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;

public class Day1 {
    public static void main(String[] args) throws IOException {
        String line = IOUtils.getInputAsString(Day1.class);

        int floor = 0;
        for (int i = 0; i < line.length(); i++) {
            floor += (line.charAt(i) == '(') ? 1 : -1;
            if (floor == -1) {
                System.out.println(i + 1);
                break;
            }
        }
    }
}
