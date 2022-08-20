package com.company.aoc2017;

import com.company.utils.IOUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day20 {

    public static void main(String[] args) throws IOException {

        AtomicInteger index = new AtomicInteger();
        List<Particle> particles = IOUtils.getInputLines(Day20.class)
                .stream()
                .map(l -> parse(index.getAndIncrement(), l))
                .toList();

        for (int i = 0; i < 10_000; i++) {
            var particleByPosition = new HashMap<Point, List<Integer>>();
            particles.stream().filter(p -> !p.isDestroyed()).forEach(p -> {
                p.tick();
                particleByPosition.computeIfAbsent(p.p, k -> new ArrayList<>()).add(p.index);
            });
            particleByPosition.forEach((k, v) -> {
                if (v.size() > 1) {
                    v.forEach(idx -> particles.get(idx).destroyed = true);
                }
            });
        }

        long count = particles.stream().filter(p -> !p.isDestroyed()).count();
        System.out.println("Total: " + particles.size() + ", survived: " + count);
    }

    static Particle parse(int index, String line) {
        List<Point> points = Arrays.stream(line.split(",\\s+"))
                .map(e -> e.substring(3, e.length() - 1).split(","))
                .map(a -> new Point(a[0], a[1], a[2]))
                .toList();
        return new Particle(index, points.get(0), points.get(1), points.get(2));
    }

    @ToString
    @RequiredArgsConstructor
    static class Particle {
        final int index;
        final Point p;
        final Point v;
        final Point a;

        boolean destroyed;


        void tick() {
            v.x += a.x;
            v.y += a.y;
            v.z += a.z;

            p.x += v.x;
            p.y += v.y;
            p.z += v.z;
        }

        long distance() {
            return Math.abs(p.x) + Math.abs(p.y) + Math.abs(p.z);
        }

        boolean isDestroyed() {
            return destroyed;
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    static class Point {
        long x;
        long y;
        long z;

        Point(String x, String y, String z) {
            this(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
        }
    }

}
