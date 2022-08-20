package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;

public class Day7 {

    public static void main(String[] args) throws IOException {
        int[] codes = Arrays.stream(IOUtils.getInputAsString(Day7.class).trim().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();


//        int[] codes1 = {3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5};
//        System.out.println(calculate(new int[]{9,8,7,6,5}, codes1));

        int max = Integer.MIN_VALUE;
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                if (j == i) continue;
                for (int k = 5; k < 10; k++) {
                    if (k == j || k == i) continue;
                    for (int m = 5; m < 10; m++) {
                        if (m == k || m == j || m == i) continue;
                        for (int n = 5; n < 10; n++) {
                            if (n == m || n == k || n == j || n == i) continue;
                            max = Math.max(max, calculate(new int[]{i, j, k, m, n}, codes));
                        }
                    }
                }
            }
        }
        System.out.println(max);
    }

    static int calculate(int[] phases, int[] codes) {
        Amplifier[] amps = Arrays.stream(phases).mapToObj(p -> new Amplifier(p, copy(codes))).toArray(Amplifier[]::new);

        int idx = 0;
        int input = 0;

        while (true) {
            int output = amps[idx % amps.length].output(input);
            idx++;
            if (output == -1) {
                break;
            }
            input = output;
        }

        return amps[amps.length - 1].lastOutput;
    }

    static int[] copy(int[] codes) {
        int[] copy = new int[codes.length];
        System.arraycopy(codes, 0, copy, 0, codes.length);
        return copy;
    }

    static class Amplifier {
        private final int phase;
        private final IntCodeComputer computer;
        private int lastOutput = -1;

        Amplifier(int phase, int[] codes) {
            this.phase = phase;
            this.computer = new IntCodeComputer(Arrays.stream(codes).mapToObj(String::valueOf).toArray(String[]::new));
        }

        int output(int input) {
            if (lastOutput == -1) {
                lastOutput = computer.computeNext(phase, input).get().intValueExact();
            } else {
                lastOutput = computer.computeNext(input).get().intValueExact();
            }
            return lastOutput;
        }
    }
}
