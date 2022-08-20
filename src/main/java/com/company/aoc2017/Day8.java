package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {

    public static void main(String[] args) throws IOException {
        List<Inst> insts = IOUtils.getInputLines(Day8.class).stream()
                .map(Day8::toInst)
                .toList();

        int max = 0;
        Map<String, Integer> reg = new HashMap<>();
        for (Inst inst : insts) {
            if (isTrue(inst.condition(), reg)) {
                int newValue = reg.getOrDefault(inst.reg(), 0) + inst.value();
                reg.put(inst.reg(), newValue);
                max = Math.max(max, newValue);
            }
        }
        System.out.println(reg.values().stream().mapToInt(Integer::intValue).max().orElse(-1));
        System.out.println("Total max: " + max);
    }

    static boolean isTrue(String cond, Map<String, Integer> reg) {
        String[] split = cond.split("\\s");
        int left = reg.getOrDefault(split[0], 0);
        int right = Integer.parseInt(split[2]);
        if (">".equals(split[1])) {
            return left > right;
        } else if (">=".equals(split[1])) {
            return left >= right;
        } else if ("<".equals(split[1])) {
            return left < right;
        } else if ("<=".equals(split[1])) {
            return left <= right;
        } else if ("!=".equals(split[1])) {
            return left != right;
        } else if ("==".equals(split[1])) {
            return left == right;
        }
        throw new IllegalArgumentException("Invalid condition: " + cond);
    }

    static Inst toInst(String line) {
        String[] split = line.split("\\s");
        return new Inst(split[0],
                "inc".equals(split[1]) ? Integer.parseInt(split[2]) : -1 * Integer.parseInt(split[2]),
                split[4] + ' ' + split[5] + ' ' + split[6]);
    }

    record Inst(String reg, int value, String condition) {

    }
}
