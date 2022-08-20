package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Day22 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.getInputLines(Day22.class);
        int size = lines.size();

        var grid = new HashMap<Pos, State>();

        for (int i = 0; i < lines.size(); i++) {
            char[] array = lines.get(i).replace(" ", "").toCharArray();
            for (int j = 0; j < array.length; j++) {
                grid.put(new Pos(j, i), array[j] == '#' ? State.INFECTED : State.CLEAN);
            }
        }

        int count = 0;
        Pos pos = new Pos(size / 2, size / 2);

        Direction direction = Direction.UP;
        for (int i = 0; i < 10000000; i++) {
            State state = Optional.ofNullable(grid.get(pos)).orElse(State.CLEAN);

            switch (state) {
                case CLEAN:
                    grid.put(pos, State.WEAK);
                    direction = turnLeft(direction);
                    break;
                case WEAK:
                    count++;
                    grid.put(pos, State.INFECTED);
                    break;
                case INFECTED:
                    grid.put(pos, State.FLAGGED);
                    direction = turnRight(direction);
                    break;
                case FLAGGED:
                    grid.put(pos, State.CLEAN);
                    direction = reverse(direction);
                    break;
            }
            pos = move(pos.x, pos.y, direction);
        }

        System.out.println(count);
    }

    static Direction turnRight(Direction current) {
        return switch (current) {
            case UP -> Direction.RIGHT;
            case DOWN -> Direction.LEFT;
            case RIGHT -> Direction.DOWN;
            case LEFT -> Direction.UP;
        };
    }

    static Direction turnLeft(Direction current) {
        return switch (current) {
            case UP -> Direction.LEFT;
            case DOWN -> Direction.RIGHT;
            case RIGHT -> Direction.UP;
            case LEFT -> Direction.DOWN;
        };
    }

    static Direction reverse(Direction current) {
        return switch (current) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case RIGHT -> Direction.LEFT;
            case LEFT -> Direction.RIGHT;
        };
    }

    static Pos move(int x, int y, Direction direction) {
        switch (direction) {
            case UP -> y--;
            case DOWN -> y++;
            case LEFT -> x--;
            case RIGHT -> x++;
        }
        return new Pos(x, y);
    }

    record Pos(int x, int y) {
    }

    enum State {
        CLEAN,
        WEAK,
        INFECTED,
        FLAGGED
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}
