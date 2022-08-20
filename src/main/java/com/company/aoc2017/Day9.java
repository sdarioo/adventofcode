package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;

public class Day9 {

    public static void main(String[] args) throws IOException {
        String text = IOUtils.getInputAsString(Day9.class);
        int value = scan(text);
        System.out.println(value);
    }

    static int scan(String text) {
        int count = 0;
        int total = 0;
        int depth = 0;
        boolean ignore = false;
        boolean trash = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (ignore) {
                ignore = false;
                continue;
            }
            if (c == '!') {
                ignore = true;
                continue;
            }
            if (!trash && c == '<') {
                trash = true;
                continue;
            }
            if (c == '>') {
                trash = false;
                continue;
            }
            if (trash) {
                count++;
                continue;
            }
            if (c == '{') {
                depth++;
            }
            if (c == '}') {
                total += depth;
                depth--;
            }
        }
        return count;
    }

}
