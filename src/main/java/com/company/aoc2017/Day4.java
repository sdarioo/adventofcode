package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Day4 {
    public static void main(String[] args) throws IOException {
        long count = IOUtils.getInputLines(Day4.class).stream()
                .map(line -> line.split("\\s"))
                .filter(array -> array.length == new HashSet<>(Arrays.asList(array)).size())
                .filter(Day4::isValid)
                .count();

        System.out.println(count);
    }

    static boolean isValid(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                String a = array[i];
                String b = array[j];
                if (a.length() == b.length() &&
                        a.chars().boxed().collect(Collectors.toSet()).equals(b.chars().boxed().collect(Collectors.toSet()))) {
                    return false;
                }
            }
        }
        return true;
    }

}
