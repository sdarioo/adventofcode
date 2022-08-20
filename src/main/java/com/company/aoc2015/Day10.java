package com.company.aoc2015;

public class Day10 {

    public static void main(String[] args) {
        String input = "1113222113";
        for (int i = 0; i < 50; i++) {
            input = convert(input);
        }
        System.out.println(input.length());
    }

    static String convert(String input) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == input.charAt(i - 1)) {
                count++;
                continue;
            }
            sb.append(count);
            sb.append(input.charAt(i - 1));
            count = 1;
        }
        sb.append(count);
        sb.append(input.charAt(input.length() - 1));
        return sb.toString();
    }

}
