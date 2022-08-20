package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public class Day23 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day23.class);

        int a = 1;
        int b = 0;
        int offset = 0;
        while (offset >= 0 && offset < lines.size()) {
            String inst = lines.get(offset);
            if (inst.startsWith("hlf ")) {
                offset++;
                if (inst.charAt(4) == 'a') {
                    a = a / 2;
                } else {
                    b = b / 2;
                }
            } else if (inst.startsWith("tpl ")) {
                offset++;
                if (inst.charAt(4) == 'a') {
                    a = 3 * a;
                } else {
                    b = 3 * b;
                }
            } else if (inst.startsWith("inc ")) {
                offset++;
                if (inst.charAt(4) == 'a') {
                    a++;
                } else {
                    b++;
                }
            } else if (inst.startsWith("jmp ")) {
                int value = Integer.parseInt(inst.substring(4));
                offset += value;
            } else if (inst.startsWith("jie ")) {
                int value = Integer.parseInt(inst.substring(7));
                int reg = inst.charAt(4) == 'a' ? a : b;
                offset += (reg % 2 == 0) ? value : 1;
            } else if (inst.startsWith("jio ")) {
                int value = Integer.parseInt(inst.substring(7));
                int reg = inst.charAt(4) == 'a' ? a : b;
                offset += (reg == 1) ? value : 1;
            }
        }
        System.out.println("b=" + b);
    }

}
