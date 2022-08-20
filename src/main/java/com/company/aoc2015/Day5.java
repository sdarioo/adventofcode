package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public class Day5 {
    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day5.class);
//        List<String>  lines = Arrays.asList("xxyxx");
        final long count = lines.stream()
                .filter(Day5::isNice)
                .count();
        System.out.println(count);
    }

    static boolean isNice(String str) {
        boolean cond1 = false;
        boolean cond2 = false;
        for (int i = 0; i < str.length(); i++) {
            if (i + 2 < str.length()) {
                cond1 |= str.charAt(i) == str.charAt(i + 2);
            }
            if (i + 3 < str.length()) {
                cond2 |= str.indexOf(str.substring(i, i + 2), i + 2) > 0;
            }
            if (cond1 && cond2) {
                break;
            }
        }
        return cond1 && cond2;
    }
}
