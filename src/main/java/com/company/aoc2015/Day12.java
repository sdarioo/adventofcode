package com.company.aoc2015;

import com.company.utils.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    public static void main(String[] args) throws IOException {
        String input = IOUtils.getInputAsString(Day12.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(input);
        System.out.println(count(jsonNode));

        int sum = 0;
        final Pattern pattern = Pattern.compile("-?\\d+");
        final Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group());
        }
        System.out.println(sum);
    }

    static int count(JsonNode node) {
        int count = 0;
        if (node instanceof NumericNode) {
            return node.asInt();
        }
        if (containsRed(node)) return 0;

        Iterator<JsonNode> elements = node.elements();
        while (elements.hasNext()) {
            count += count(elements.next());
        }
        return count;
    }

    private static boolean containsRed(JsonNode node) {
        if (node instanceof ObjectNode) {
            Iterator<JsonNode> elements = node.elements();
            while (elements.hasNext()) {
                if ("red".equals(elements.next().textValue())) {
                    return true;
                }
            }
        }
        return false;
    }


}
