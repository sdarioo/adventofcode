package com.company.aoc2017;

import java.util.stream.IntStream;

public class Day10 {

    public static void main(String[] args) {
        //String input = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70";
        String input = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70";

        System.out.println(knotHash(input));
    }

    static String knotHash(String input) {
        int[] lengths = input.chars().toArray();
        int[] suffix = {17, 31, 73, 47, 23};
        lengths = add(lengths, suffix);

        int[] list = IntStream.range(0, 256).toArray();

        int pos = 0;
        for (int i = 0; i < 64 * lengths.length; i++) {
            int length = lengths[i % lengths.length];
            reverse(list, pos, length);
            pos = (pos + length + i) % list.length;
        }

        StringBuilder sb = new StringBuilder();
        int[] hash = new int[list.length / 16];
        for (int i = 0; i < hash.length; i++) {
            int start = i * 16;
            int result = list[start];
            for (int j = 1; j < 16; j++) {
                result ^= list[start + j];
            }
            hash[i] = result;
            String hex = Integer.toHexString(result);
            sb.append(hex.length() == 2 ? hex : '0' + hex);
        }
        return sb.toString().trim();
    }

    static void reverse(int[] list, int idx, int len) {
        for (int i = 0; i < len / 2; i++) {
            int x = (idx + i) % list.length;
            int y = (idx + len - 1 - i) % list.length;
            int temp = list[x];
            list[x] = list[y];
            list[y] = temp;
        }
    }

    static int[] add(int[] x, int[] y) {
        int[] result = new int[x.length + y.length];
        System.arraycopy(x, 0, result, 0, x.length);
        System.arraycopy(y, 0, result, x.length, y.length);
        return result;
    }
}
