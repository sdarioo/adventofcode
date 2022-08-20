package com.company.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public final class IOUtils {
    private IOUtils() {

    }

    public static String getInputAsString(Class<?> clazz) {
        return getInputLines(clazz).get(0);
    }

    public static List<String> getInputLines(Class<?> clazz) {
        String path = '/' + getPackageName(clazz) + "/input/" + clazz.getSimpleName().toLowerCase() + ".txt";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clazz.getResourceAsStream(path)))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPackageName(Class<?> clazz) {
        int index = clazz.getPackageName().lastIndexOf('.');
        return index >= 0
                ? clazz.getPackageName().substring(index + 1)
                : clazz.getPackageName();
    }

}
