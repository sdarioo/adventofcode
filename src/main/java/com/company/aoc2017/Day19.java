package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.List;

public class Day19 {

    public static void main(String[] args) throws IOException {
        List<String> list = IOUtils.getInputLines(Day19.class);

        StringBuilder sb = new StringBuilder();
        int x = list.get(0).indexOf('|');
        int y = 0;

        Direction direction = Direction.DOWN;

        int count = 0;
        while (true) {
            if (x < 0 || y < 0 || y >= list.size() || x >= list.get(y).length()) {
                break;
            }
            char c = list.get(y).charAt(x);
            if (c == ' ') {
                break;
            }

            if (Character.isAlphabetic(c)) {
                sb.append(c);
            } else if (c == '+') {
                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    direction = get(list, x, y - 1) == ' ' ? Direction.DOWN : Direction.UP;
                } else {
                    direction = get(list, x - 1, y) == ' ' ? Direction.RIGHT : Direction.LEFT;
                }
            }

            int[] point = move(x, y, direction);
            x = point[0];
            y = point[1];
            count++;
        }

        System.out.println(count);
    }

    static int[] move(int x, int y, Direction direction) {
        int[] result = {x, y};
        switch (direction) {
            case UP -> result[1]--;
            case DOWN -> result[1]++;
            case LEFT -> result[0]--;
            case RIGHT -> result[0]++;
        }
        return result;
    }

    static char get(List<String> list, int x, int y) {
        try {
            return list.get(y).charAt(x);
        } catch (Exception e) {
            return ' ';
        }
    }

    enum Direction {
        UP, DOWN, RIGHT, LEFT
    }

}
