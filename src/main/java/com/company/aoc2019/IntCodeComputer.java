package com.company.aoc2019;

import java.math.BigInteger;
import java.util.*;

public class IntCodeComputer {

    private final Map<BigInteger, BigInteger> codeMap = new HashMap<>();
    private BigInteger index = BigInteger.ZERO;
    private BigInteger relativeBase = BigInteger.ZERO;

    IntCodeComputer(String input) {
        this(input.split(","));
    }

    IntCodeComputer(String[] codes) {
        for (int i = 0; i < codes.length; i++) {
            codeMap.put(BigInteger.valueOf(i), new BigInteger(codes[i]));
        }
    }

    public List<BigInteger> computeAll(int... input) {
        List<BigInteger> result = new ArrayList<>();
        Optional<BigInteger> next = computeNext(input);
        while (next.isPresent()) {
            result.add(next.get());
            next = computeNext(input);
        }
        return result;
    }

    public Optional<BigInteger> computeNext(int... input) {
        return computeNext(Arrays.stream(input).iterator());
    }

    public Optional<BigInteger> computeNext(Iterator<Integer> input) {
        Optional<BigInteger> output = Optional.empty();

        while (output.isEmpty() && getInstruction() != 99) {
            int code = getInstruction();
            switch (code) {
                case 1 -> {
                    setParam(3, getParam(1).add(getParam(2)));
                    index = add(index, 4);
                }
                case 2 -> {
                    setParam(3, getParam(1).multiply(getParam(2)));
                    index = add(index, 4);
                }
                case 3 -> {
                    setParam(1, BigInteger.valueOf(input.next()));
                    index = add(index, 2);
                }
                case 4 -> {
                    output = Optional.of(getParam(1));
                    index = add(index, 2);
                }
                case 5 -> index = !getParam(1).equals(BigInteger.ZERO)
                        ? getParam(2)
                        : add(index, 3);
                case 6 -> index = getParam(1).equals(BigInteger.ZERO)
                        ? getParam(2)
                        : add(index, 3);
                case 7 -> {
                    setParam(3, getParam(1).compareTo(getParam(2)) < 0
                            ? BigInteger.ONE
                            : BigInteger.ZERO);
                    index = add(index, 4);
                }
                case 8 -> {
                    setParam(3, getParam(1).equals(getParam(2))
                            ? BigInteger.ONE
                            : BigInteger.ZERO);
                    index = add(index, 4);
                }
                case 9 -> {
                    relativeBase = relativeBase.add(getParam(1));
                    index = add(index, 2);
                }
                default -> throw new IllegalArgumentException("Illegal code: " + getCode(index));
            }
        }
        return output;
    }

    BigInteger getCode(BigInteger index) {
        return codeMap.getOrDefault(index, BigInteger.ZERO);
    }

    BigInteger getParam(int position) {
        int paramMode = getParamMode(position);
        return switch (paramMode) {
            case 0 -> getCode(getCode(add(index, position)));
            case 1 -> getCode(add(index, position));
            case 2 -> getCode(relativeBase.add(getCode(add(index, position))));
            default -> throw new IllegalArgumentException("Invalid mode: " + paramMode);
        };
    }

    void setParam(int position, BigInteger value) {
        int paramMode = getParamMode(position);
        switch (paramMode) {
            case 0 -> codeMap.put(getCode(add(index, position)), value);
            case 2 -> codeMap.put(relativeBase.add(getCode(add(index, position))), value);
            default -> throw new IllegalArgumentException("Invalid mode: " + paramMode);
        }
    }

    int getInstruction() {
        BigInteger opcode = getCode(index);
        return opcode.intValueExact() % 100;
    }

    int getParamMode(int position) {
        BigInteger opcode = getCode(index);
        return (opcode.intValueExact() % (int) Math.pow(10, position + 2)) / (int) Math.pow(10, position + 1);
    }

    private static BigInteger add(BigInteger value, int offset) {
        return value.add(BigInteger.valueOf(offset));
    }
}
