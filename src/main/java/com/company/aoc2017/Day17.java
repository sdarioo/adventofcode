package com.company.aoc2017;

public class Day17 {

    public static void main(String[] args) {

        int steps = 355;

        System.out.println(part2(steps, 50_000_000));

//        List<Integer> buffer = new LinkedList<>();
//        buffer.add(0);
//
//        int pos = 0;
//        for (int i = 1; i <= 50_000_000; i++) {
//            pos = (pos + steps) % buffer.size() + 1;
//
//            ListIterator<Integer> it = buffer.listIterator();
//            for (int j = 0; j < pos; j++) {
//                it.next();
//            }
//            it.add(i);
//        }
//
//        int idx = buffer.indexOf(0);
//        System.out.println(buffer.get(idx + 1));

    }

    static int part2(int steps, int count) {
        int result = -1;
        int pos = 0;
        for (int i = 1; i <= count; i++) {
            pos = (pos + steps) % i + 1;
            if (pos == 1) {
                result = i;
            }
        }
        return result;
    }

}
