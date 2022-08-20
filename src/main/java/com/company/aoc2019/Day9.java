package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

public class Day9 {

    public static void main(String[] args) throws IOException {
        String input = IOUtils.getInputAsString(Day9.class);

        IntCodeComputer computer = new IntCodeComputer(input);
        int[] in = new int[]{2};
        Optional<BigInteger> next = computer.computeNext(in);
        while (next.isPresent()) {
            System.out.println(next.get());
            next = computer.computeNext(in);
        }
    }

}
