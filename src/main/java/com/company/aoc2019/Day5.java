package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;

public class Day5 {

    public static void main(String[] args) throws IOException {
        String text = IOUtils.getInputAsString(Day5.class);

        int[] codes = Arrays.stream(text.trim().split(",")).mapToInt(Integer::parseInt).toArray();

        int index = 0;
        int input = 1;
        while (getInstruction(codes[index]) != 99) {

            int code = getInstruction(codes[index]);
            System.out.print(code);
            switch (code) {
                case 1 -> {
                    codes[codes[index + 3]] = getParamValue(codes, index, 1) + getParamValue(codes, index, 2);
                    index += 4;
                }
                case 2 -> {
                    codes[codes[index + 3]] = getParamValue(codes, index, 1) * getParamValue(codes, index, 2);
                    index += 4;
                }
                case 3 -> {
                    codes[codes[index + 1]] = input;
                    index += 2;
                }
                case 4 -> {
                    int output = getParamValue(codes, index, 1);
                    //        System.out.println(output);
                    index += 2;
                }
                case 5 -> {
                    index = getParamValue(codes, index, 1) != 0
                            ? getParamValue(codes, index, 2)
                            : index + 3;
                }
                case 6 -> {
                    index = getParamValue(codes, index, 1) == 0
                            ? getParamValue(codes, index, 2)
                            : index + 3;
                }
                case 7 -> {
                    codes[codes[index + 3]] = getParamValue(codes, index, 1) < getParamValue(codes, index, 2)
                            ? 1
                            : 0;
                    index += 4;
                }
                case 8 -> {
                    codes[codes[index + 3]] = getParamValue(codes, index, 1) == getParamValue(codes, index, 2)
                            ? 1
                            : 0;
                    index += 4;
                }
                default -> throw new IllegalArgumentException("Illegal code: " + codes[index]);
            }
            if (index >= codes.length) {
                index = index % codes.length;
            }
        }
    }

    static int getInstruction(int opcode) {
        return opcode % 100;
    }

    static int getParamValue(int[] opcodes, int index, int position) {
        int paramMode = getParamMode(opcodes[index], position);
        return switch (paramMode) {
            case 0 -> opcodes[opcodes[index + position]];
            case 1 -> opcodes[index + position];
            default -> throw new IllegalArgumentException("Invalid mode: " + paramMode);
        };
    }

    static int getParamMode(int opcode, int position) {
        return (opcode % (int) Math.pow(10, position + 2)) / (int) Math.pow(10, position + 1);
    }

}
