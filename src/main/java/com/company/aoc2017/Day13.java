package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day13 {
    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day13.class);
//        List<String> lines = List.of(
//                "0: 3",
//                "1: 2",
//                "4: 4",
//                "6: 4");

        Map<Integer, Scanner> scanners = lines.stream()
                .map(l -> l.split(":"))
                .map(a -> new Scanner(Integer.parseInt(a[0].trim()), Integer.parseInt(a[1].trim())))
                .collect(Collectors.toMap(Scanner::depth, s -> s));

        int max = scanners.keySet().stream().mapToInt(Integer::intValue).max().orElseThrow();

        int delay = 1;
        while (true) {

            boolean caught = false;
            for (int i = 0; i <= max; i++) {
                Scanner scanner = scanners.get(i);
                if (scanner != null && scanner.isCaught(delay + i)) {
                    caught = true;
                    break;
                }
            }
            if (!caught) {
                break;
            }
            delay++;
        }
        System.out.println(delay);
    }

    record Scanner(int depth, int range) {
        boolean isCaught(int nano) {
            return nano % (2 * (range - 1)) == 0;
        }
    }
}
