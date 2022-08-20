package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19 {
    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day19.class);

        String m = "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";

        Map<Character, List<String[]>> map = lines.stream()
                .map(l -> l.split("=>"))
                .map(a -> new String[]{a[1].trim(), a[0].trim()})
                .collect(Collectors.groupingBy(a -> a[0].charAt(0)));

        System.out.println("start");

        int steps = 0;
        while (!m.equals("e")) {
            String next = m;
            for (int i = 0; i < m.length(); i++) {
                for (String[] pair : map.getOrDefault(m.charAt(i), Collections.emptyList())) {
                    if (m.startsWith(pair[0], i)) {
                        String r = replace(m, i, pair[0].length(), pair[1]);
                        if (r.length() <= next.length()) {
                            next = r;
                        }
                    }
                }
            }
            if (next.equals(m)) {
                System.out.println("dupa!");
                break;
            }
            m = next;
            steps++;
        }
        System.out.println("DONE: " + m + " min: " + steps);

//        Result result = new Result();
//        reduce(m, map, 0, result);
//        System.out.println(result.min);
    }

    static String replace(String molecule, int idx, int len, String replacement) {
        return (idx > 0 ? molecule.substring(0, idx) : "") +
                replacement +
                (idx < molecule.length() - 1 ? molecule.substring(idx + len) : "");
    }


}
