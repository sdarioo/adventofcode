package com.company.aoc2015;

public class Day11 {
    static int A_INT = (int) 'a';
    static int Z_INT = (int) 'z';

    public static void main(String[] args) {
        String input = "vzbxxyzz";
        int[] pass = input.chars().toArray();

        while (true) {
            increment(pass, pass.length - 1);
            if (isValid(pass)) {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i : pass) {
            sb.append((char) i);
        }
        System.out.println(sb.toString());
    }

    static void increment(int[] pass, int index) {
        pass[index] = next(pass[index]);
        if (pass[index] == A_INT) {
            increment(pass, index - 1);
        }
    }

    static boolean isValid(int[] pass) {
        int pairs = 0;
        for (int i = 1; i < pass.length; i++) {
            if (pass[i] == pass[i - 1]) {
                pairs++;
                i += 1;
            }
        }
        boolean found = false;
        for (int i = 2; i < pass.length; i++) {
            if (pass[i] == pass[i - 1] + 1 && pass[i] == pass[i - 2] + 2) {
                found = true;
                break;
            }
        }
        return pairs == 2 && found;
    }

    static int next(int current) {
        if (current == Z_INT) {
            return A_INT;
        }
        int next = current + 1;
        return next == 'l' || next == 'i' || next == 'o' ? next(next) : next;
    }

}
