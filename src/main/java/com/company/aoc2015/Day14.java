package com.company.aoc2015;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {

    public static void main(String[] args) throws IOException {
        List<Car> cars = IOUtils.getInputLines(Day14.class).stream()
                .map(l -> l.split("\\s"))
                .map(a -> new Car(a[0], Integer.parseInt(a[3]), Integer.parseInt(a[6]), Integer.parseInt(a[13])))
                .toList();

        int[] distance = new int[cars.size()];
        int[] points = new int[cars.size()];
        for (int i = 1; i <= 2503; i++) {
            for (int j = 0; j < cars.size(); j++) {
                distance[j] = distance(cars.get(j), i);
            }
            maxIndex(distance).forEach(idx -> points[idx]++);
        }
        Arrays.sort(points);

        //int result = cars.stream().mapToInt(car -> distance(car, 2503)).max().orElse(-1);
        System.out.println(points[points.length - 1]);
    }

    static List<Integer> maxIndex(int[] array) {
        var result = new ArrayList<>(List.of(0));
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[result.get(0)]) {
                result.clear();
                result.add(i);
            } else if (array[i] == array[result.get(0)]) {
                result.add(i);
            }
        }
        return result;
    }

    static int distance(Car car, int seconds) {
        int x = (seconds / (car.runSec + car.restSec));
        int y = (seconds % (car.runSec + car.restSec));
        return (x * car.velocity * car.runSec) +
                (y <= car.runSec
                        ? y * car.velocity
                        : car.runSec * car.velocity);
    }

    private record Car(String name, int velocity, int runSec, int restSec) {
    }
}
