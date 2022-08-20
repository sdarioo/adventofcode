package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day3 {

    public static void main(String[] args) throws IOException {
        String text = IOUtils.getInputAsString(Day3.class);
        int[] pos1 = {0, 0};
        int[] pos2 = {0, 0};
        Set<String> delivered = new HashSet<>();
        delivered.add(pos1[0] + "-" + pos1[1]);

        for (int i = 0; i < text.length(); i += 2) {
            move(text.charAt(i), pos1);
            delivered.add(pos1[0] + "-" + pos1[1]);

            if (i + 1 < text.length()) {
                move(text.charAt(i + 1), pos2);
                delivered.add(pos2[0] + "-" + pos2[1]);
            }
        }
        System.out.println(delivered.size());
    }

    static void move(char c, int[] pos) {
        if (c == '^') pos[1]++;
        if (c == 'v') pos[1]--;
        if (c == '>') pos[0]++;
        if (c == '<') pos[0]--;
    }
}
