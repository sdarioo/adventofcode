package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) throws IOException {
        String input = IOUtils.getInputAsString(Day11.class);

        Point loc = new Point(0, 0);
        Direction direction = new Direction.Up();
        Map<Point, Integer> panel = new HashMap<>();

        setColor(panel, loc, 1);

        IntCodeComputer computer = new IntCodeComputer(input);
        while (true) {
            int color = getColor(panel, loc);
            Optional<BigInteger> newColor = computer.computeNext(color);
            if (newColor.isEmpty()) {
                break;
            }
            setColor(panel, loc, newColor.get().intValue());

            Optional<BigInteger> turn = computer.computeNext(color);
            if (turn.isEmpty()) {
                break;
            }
            direction = direction.turn(turn.get().intValue());
            loc = direction.move(loc);
        }

        IntSummaryStatistics rangeX = panel.keySet().stream().collect(Collectors.summarizingInt(Point::x));
        IntSummaryStatistics rangeY = panel.keySet().stream().collect(Collectors.summarizingInt(Point::y));

        for (int y = rangeY.getMin(); y <= rangeY.getMax(); y++) {
            for (int x = rangeX.getMin(); x <= rangeX.getMax(); x++) {
                int color = getColor(panel, new Point(x, y));
                System.out.print(color == 0 ? '.' : '#');
            }
            System.out.println();
        }
    }

    static void setColor(Map<Point, Integer> panel, Point loc, int color) {
        panel.put(loc, color);
    }

    static int getColor(Map<Point, Integer> panel, Point loc) {
        return panel.getOrDefault(loc, 0);
    }

    record Point(int x, int y) {
    }

    sealed interface Direction {
        Point move(Point loc);

        Direction turn(int leftRight);

        record Up() implements Direction {
            public Point move(Point loc) {
                return new Point(loc.x, loc.y - 1);
            }

            @Override
            public Direction turn(int leftRight) {
                return leftRight == 0 ? new Left() : new Right();
            }
        }

        record Down() implements Direction {
            public Point move(Point loc) {
                return new Point(loc.x, loc.y + 1);
            }

            @Override
            public Direction turn(int leftRight) {
                return leftRight == 0 ? new Right() : new Left();
            }
        }

        record Left() implements Direction {
            public Point move(Point loc) {
                return new Point(loc.x - 1, loc.y);
            }

            @Override
            public Direction turn(int leftRight) {
                return leftRight == 0 ? new Down() : new Up();
            }
        }

        record Right() implements Direction {
            public Point move(Point loc) {
                return new Point(loc.x + 1, loc.y);
            }

            @Override
            public Direction turn(int leftRight) {
                return leftRight == 0 ? new Up() : new Down();
            }
        }
    }

}
