package com.company.aoc2019;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

public class Day12 {

    public static void main(String[] args) {
        List<Moon> moons = List.of(
                new Moon("a", -15, 1, 4),
                new Moon("b", 1, -10, -8),
                new Moon("c", -5, 4, 9),
                new Moon("d", 4, 6, -2));

        long stepsX = getCycleSize(moons, Position::getX);
        moons.forEach(Moon::reset);
        long stepsY = getCycleSize(moons, Position::getY);
        moons.forEach(Moon::reset);
        long stepsZ = getCycleSize(moons, Position::getZ);

        BigInteger xxx = BigInteger.valueOf(stepsX).multiply(BigInteger.valueOf(stepsY)).multiply(BigInteger.valueOf(stepsZ));
        System.out.println(xxx);

        long i = 2;
        while (true) {

            BigInteger result = BigInteger.valueOf(stepsY).multiply(BigInteger.valueOf(i));
            if (result.mod(BigInteger.valueOf(stepsX)).intValue() == 0 &&
                    result.mod(BigInteger.valueOf(stepsZ)).intValue() == 0) {
                System.out.println(result);
                break;
            }
            i++;
        }
    }

    private static long getCycleSize(List<Moon> moons, Function<Position, Integer> foo) {
        long count = 0;
        do {
            applyGravity(moons);
            applyVelocity(moons);
            count++;

        } while (!isCycleDetected(moons, foo));
        return count;
    }

    private static boolean isCycleDetected(List<Moon> moons, Function<Position, Integer> foo) {
        for (Moon moon : moons) {
            if (foo.apply(moon.velocity) != 0 ||
                    !foo.apply(moon.initialPosition).equals(foo.apply(moon.position))) {
                return false;
            }
        }
        return true;
    }

    private static void applyVelocity(List<Moon> moons) {
        for (Moon moon : moons) {
            moon.position.x += moon.velocity.x;
            moon.position.y += moon.velocity.y;
            moon.position.z += moon.velocity.z;
        }
    }

    private static void applyGravity(List<Moon> moons) {
        for (int i = 0; i < moons.size(); i++) {
            Moon moon1 = moons.get(i);
            for (int j = i + 1; j < moons.size(); j++) {
                Moon moon2 = moons.get(j);
                if (moon1.position.x != moon2.position.x) {
                    moon1.velocity.x += moon1.position.x > moon2.position.x ? -1 : 1;
                    moon2.velocity.x += moon2.position.x > moon1.position.x ? -1 : 1;
                }
                if (moon1.position.y != moon2.position.y) {
                    moon1.velocity.y += moon1.position.y > moon2.position.y ? -1 : 1;
                    moon2.velocity.y += moon2.position.y > moon1.position.y ? -1 : 1;
                }
                if (moon1.position.z != moon2.position.z) {
                    moon1.velocity.z += moon1.position.z > moon2.position.z ? -1 : 1;
                    moon2.velocity.z += moon2.position.z > moon1.position.z ? -1 : 1;
                }
            }
        }
    }

    @Data
    static class Position {
        int x;
        int y;
        int z;

        public String toString() {
            return String.format("<%d, %d, %d>", x, y, z);
        }
    }

    @RequiredArgsConstructor
    static class Moon {
        final String name;
        Position initialPosition = new Position();
        Position position = new Position();
        Position velocity = new Position();

        Moon(String name, int x, int y, int z) {
            this.name = name;
            this.position.x = x;
            this.position.y = y;
            this.position.z = z;

            this.initialPosition.x = x;
            this.initialPosition.y = y;
            this.initialPosition.z = z;
        }

        int potentialEnergy() {
            return Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z);
        }

        int kineticEnergy() {
            return Math.abs(velocity.x) + Math.abs(velocity.y) + Math.abs(velocity.z);
        }

        int totalEnergy() {
            return potentialEnergy() * kineticEnergy();
        }

        void reset() {
            position.x = initialPosition.x;
            position.y = initialPosition.y;
            position.z = initialPosition.z;
            velocity.x = 0;
            velocity.y = 0;
            velocity.z = 0;
        }

        @Override
        public String toString() {
            return String.format("pos=%s, vel=%s", position, velocity);
        }
    }

}
