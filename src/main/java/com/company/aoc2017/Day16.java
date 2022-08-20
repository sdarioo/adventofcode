package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day16 {

    public static void main(String[] args) throws IOException {
        char[] input = "abcdefghijklmnop".toCharArray();

        String text = IOUtils.getInputAsString(Day16.class);

        long startTime = System.currentTimeMillis();

        Map<String, Integer> unique = new HashMap<>();

        long count = 1_000_000_000L % 42;
        for (int i = 0; i < count; i++) {
            for (String inst : text.split(",")) {
                char c = inst.charAt(0);
                input = switch (c) {
                    case 's' -> spin(input, Integer.parseInt(inst.substring(1)));
                    case 'x' -> exchange(input, inst.substring(1));
                    case 'p' -> partner(input, inst.substring(1));
                    default -> throw new IllegalArgumentException("Unexpected: " + c);
                };
            }
            var str = new String(input);
            if (unique.containsKey(str)) {
//                System.out.println("Loop from: " + unique.get(str) + " to: " + i);
//                break;
            } else {
                unique.put(str, i);
            }
        }

        System.out.println(new String(input));
        System.out.println("Total: " + (System.currentTimeMillis() - startTime));
    }

    static char[] spin(char[] input, int len) {
        int idx = input.length - len;
        char[] result = new char[input.length];
        System.arraycopy(input, idx, result, 0, len);
        System.arraycopy(input, 0, result, len, idx);
        return result;
    }

    static char[] exchange(char[] input, String str) {
        Integer[] args = Arrays.stream(str.trim().split("/")).map(Integer::parseInt).toArray(Integer[]::new);
        swap(input, args[0], args[1]);
        return input;
    }

    static char[] partner(char[] input, String str) {
        int[] args = Arrays.stream(str.trim().split("/")).mapToInt(s -> s.charAt(0)).toArray();
        swap(input, index(input, (char) args[0]), index(input, (char) args[1]));
        return input;
    }

    static void swap(char[] array, int x, int y) {
        char temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    static int index(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        throw new IllegalArgumentException("Not found: " + c);
    }
}
