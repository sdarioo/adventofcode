package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class Day8 {

    public static void main(String[] args) throws IOException {
        String text = IOUtils.getInputAsString(Day8.class);

        int width = 25;
        int height = 6;
        int count = width * height;

        int[][] image = new int[6][25];
        IntStream.range(0, count).forEach(i -> image[i / width][i % width] = 2);
        printImage(image);

        int index = text.length();
        while (index > 0) {
            String layer = text.substring(index - count, index);
            apply(image, layer);
            index -= count;
        }
        printImage(image);
    }

    private static void apply(int[][] image, String layer) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                int c = (int) layer.charAt(i * image[i].length + j) - 48;
                if (c != 2) {
                    image[i][j] = c;
                }
            }
        }
    }

    private static void printImage(int[][] image) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                System.out.print(image[i][j] == 1 ? 'x' : ' ');
            }
            System.out.println();
        }
    }

}
