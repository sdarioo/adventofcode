package com.company.aoc2019;

import com.company.utils.IOUtils;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Optional;

public class Day13 {

    public static void main(String[] args) throws IOException {
        String input = IOUtils.getInputAsString(Day13.class);

        input = "2" + input.substring(1);

        Board board = new Board(new char[21][38]);
        Joystick joystick = new Joystick(board);
        IntCodeComputer computer = new IntCodeComputer(input);

        int score = 0;
        while (true) {
            Optional<BigInteger> x = computer.computeNext(joystick);
            Optional<BigInteger> y = computer.computeNext(joystick);
            Optional<BigInteger> id = computer.computeNext(joystick);
            if (x.isEmpty() || y.isEmpty() || id.isEmpty()) {
                break;
            }
            if (x.get().intValue() == -1 && y.get().intValue() == 0) {
                score = id.get().intValue();
            } else {
                board.update(x.get().intValue(), y.get().intValue(), id.get().intValue());
            }
        }
        System.out.println("Score: " + score);
    }

    @RequiredArgsConstructor
    static class Board {
        private final char[][] grid;
        private int paddleX = -1;
        private int ballX = -1;

        void draw() {
            for (char[] chars : grid) {
                for (int j = 0; j < grid[0].length; j++) {
                    System.out.print(chars[j]);
                }
                System.out.println();
            }
        }

        void update(int x, int y, int id) {
            char c = switch (id) {
                case 0 -> ' ';
                case 1 -> 'x';
                case 2 -> '*';
                case 3 -> '-';
                case 4 -> 'o';
                default -> throw new IllegalArgumentException("Illegal: " + id);
            };
            grid[y][x] = c;
            if (id == 4) {
                ballX = x;
            } else if (id == 3) {
                paddleX = x;
            }
        }
    }

    @RequiredArgsConstructor
    static class Joystick implements Iterator<Integer> {
        private final Board board;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            if (board.paddleX == board.ballX) {
                return 0;
            }
            return board.paddleX < board.ballX ? 1 : -1;
        }
    }
}
