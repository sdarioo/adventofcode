package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day7 {

    public static void main(String[] args) throws IOException {
        var lines = IOUtils.getInputLines(Day7.class);

        Map<String, String> gates = lines.stream()
                .map(l -> l.split("->"))
                .collect(Collectors.toMap(a -> a[1].trim(), a -> a[0].trim()));

        gates.put("b", String.valueOf(46065));
        System.out.println(getValue(gates, "a"));
    }

    static int getValue(Map<String, String> gates, String wire) {
        Optional<Integer> number = getNumber(wire);
        if (number.isPresent()) {
            return number.get();
        }
        String gate = gates.get(wire);

        int result;
        if (gate.startsWith("NOT ")) {
            result = toUInt16(~getValue(gates, gate.substring(4)));
        } else if (gate.contains(" LSHIFT ")) {
            result = toUInt16(getValue(gates, gate.substring(0, gate.indexOf(' ')))
                    << Integer.parseInt(gate.substring(gate.lastIndexOf(' ') + 1)));
        } else if (gate.contains(" RSHIFT ")) {
            result = toUInt16(getValue(gates, gate.substring(0, gate.indexOf(' ')))
                    >> Integer.parseInt(gate.substring(gate.lastIndexOf(' ') + 1)));
        } else if (gate.contains(" AND ")) {
            result = toUInt16(getValue(gates, gate.substring(0, gate.indexOf(' '))) &
                    getValue(gates, gate.substring(gate.lastIndexOf(' ') + 1)));
        } else if (gate.contains(" OR ")) {
            result = toUInt16(getValue(gates, gate.substring(0, gate.indexOf(' '))) |
                    getValue(gates, gate.substring(gate.lastIndexOf(' ') + 1)));
        } else {
            result = getValue(gates, gate);
        }
        gates.put(wire, String.valueOf(result));
        return result;
    }

    static int toUInt16(int value) {
        return value & 0xFFFF;
    }

    static Optional<Integer> getNumber(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
