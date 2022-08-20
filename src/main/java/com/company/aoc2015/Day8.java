package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;

public class Day8 {
    public static void main(String[] args) throws IOException {
        var lines = IOUtils.getInputLines(Day8.class);
        // System.out.println(lines.get(0).length());
        //  lines = Arrays.asList("\"\\x27\"");

        long codeCount = lines.stream().mapToInt(String::length).sum();
        long inMemoryCount = lines.stream().mapToInt(Day8::getRawLength).sum();
        long escapedLength = lines.stream().mapToInt(Day8::getEscapedLength).sum();
        System.out.println(codeCount - inMemoryCount);
        System.out.println(escapedLength - codeCount);
    }

    private static int getEscapedLength(String line) {
        int length = 2; // begin and end quotes
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                length += 2;
            } else if (c == '\\') {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    private static int getRawLength(String line) {
        int length = 0;
        boolean escape = false;
        for (int i = 1; i < line.length() - 1; i++) {
            char c = line.charAt(i);
            if (c == '\\' && !escape) {
                escape = true;
                continue;
            }
            if (c == 'x' && escape) {
                i += 2;
            }
            escape = false;
            length++;
        }
        return length;
    }
}
