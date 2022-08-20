package com.company.aoc2015;

import java.math.BigDecimal;

public class Day25 {

    public static void main(String[] args) {
        int row = 2978;
        int col = 3083;

        int x = 1;
        int y = 1;
        BigDecimal value = new BigDecimal(20151125);
        while (true) {
            BigDecimal next = value.multiply(new BigDecimal(252533));
            value = next.divideAndRemainder(new BigDecimal(33554393))[1];

            if (y == 1) {
                y = x + 1;
                x = 1;
            } else {
                x++;
                y--;
            }
            if (y == row && x == col) {
                System.out.println(value);
                break;
            }
        }

        System.out.println(value);
    }

}
