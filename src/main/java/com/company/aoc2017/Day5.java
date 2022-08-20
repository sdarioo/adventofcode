package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;

public class Day5 {
    public static void main(String[] args) throws IOException {
        int[] code = IOUtils.getInputLines(Day5.class).stream().mapToInt(Integer::parseInt).toArray();

        int index = 0;
        int count = 0;
        while (index >= 0 && index < code.length) {
            int newIndex = index + code[index];
            if (code[index] >= 3) {
                code[index]--;
            } else {
                code[index]++;
            }
            index = newIndex;
            count++;
        }
        System.out.println(count);
    }
}
