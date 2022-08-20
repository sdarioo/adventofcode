package com.company.aoc2017;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {

    public static void main(String[] args) throws IOException {
        List<Node> nodes = IOUtils.getInputLines(Day7.class).stream()
                .map(Day7::toNode)
                .toList();

        Set<String> names = nodes.stream().map(Node::name).collect(Collectors.toSet());
        Set<String> child = nodes.stream().flatMap(n -> n.children.stream()).collect(Collectors.toSet());

        names.removeAll(child);
        String rootName = names.iterator().next();

        Map<String, Node> nodeMap = nodes.stream().collect(Collectors.toMap(Node::name, n -> n));
        Node root = nodeMap.get(rootName);

        findUnbalanced(root, null, nodeMap);
    }

    static void findUnbalanced(Node node, Node parent, Map<String, Node> nodeMap) {
        if (isBalanced(node, nodeMap)) {
            throw new IllegalArgumentException("Node is balanced!");
        }
        List<String> unbalanced = node.children.stream().filter(n -> !isBalanced(nodeMap.get(n), nodeMap)).toList();
        if (unbalanced.isEmpty()) {
            System.out.println("I'm unbalanced!!!");
            String sibling = parent.children.stream().filter(n -> !n.equals(node.name)).findFirst().orElseThrow();
            int siblingTotalWeight = totalWeight(nodeMap.get(sibling), nodeMap);
            int expectedChildTotalWeight = (siblingTotalWeight - node.weight) / node.children.size();

            for (String child : node.children) {
                Node childNode = nodeMap.get(child);
                if (totalWeight(childNode, nodeMap) != expectedChildTotalWeight) {
                    System.out.println("Child with incorrect weight found. Expected: " +
                            (expectedChildTotalWeight - (totalWeight(childNode, nodeMap) - childNode.weight)));
                }
            }

        } else if (unbalanced.size() == 1) {
            findUnbalanced(nodeMap.get(unbalanced.get(0)), node, nodeMap);
        } else {
            throw new IllegalArgumentException("More than one children unbalanced: " + unbalanced);
        }
    }

    static int totalWeight(Node node, Map<String, Node> nodeMap) {
        return node.weight + node.children.stream().map(nodeMap::get).mapToInt(n -> totalWeight(n, nodeMap)).sum();
    }

    static boolean isBalanced(Node node, Map<String, Node> map) {
        return node.children.isEmpty()
                || node.children.stream().map(map::get).mapToInt(n -> totalWeight(n, map)).distinct().count() == 1;
    }

    static Node toNode(String line) {
        String name = line.substring(0, line.indexOf(' '));
        int weight = Integer.parseInt(line.substring(line.indexOf('(') + 1, line.indexOf(')')));

        Set<String> children;
        if (line.indexOf("->") > 0) {
            children = Arrays.stream(line.substring(line.indexOf("->") + 2).split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        } else {
            children = new HashSet<>();
        }

        return new Node(name, weight, children);
    }

    record Node(String name, int weight, Set<String> children) {

    }
}
