package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws IOException {

        List<String> lines = IOUtils.getInputLines(Day1.class);
//        lines = List.of("100756");

        int sum = lines.stream()
                .map(Integer::parseInt)
                .map(Module::new)
                .mapToInt(Module::fuel)
                .sum();

//        int fuelMass = sum;
//        while (fuelMass > 0) {
//            Module m = new Module(fuelMass);
//            fuelMass = Math.max(m.fuel(), 0);
//            sum += fuelMass;
//        }

        System.out.println(sum);
    }

    record Module(int mass) {

        int fuel() {
            if (mass > 0) {
                int fuel = mass / 3 - 2;
                return Math.max(0, fuel + new Module(fuel).fuel());
            }
            return 0;
        }
    }
}
