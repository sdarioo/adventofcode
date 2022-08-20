package com.company.aoc2019;

import com.company.utils.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) throws IOException {

//        System.out.println(new Station(0, 0).angle(new Station(10, 10)));
//        System.out.println(new Station(0, 0).angle(new Station(0, 10)));
//        System.out.println(new Station(0, 0).angle(new Station(-10, 10)));
//        System.out.println(new Station(0, 0).angle(new Station(-10, 0)));
//        System.out.println(new Station(0, 0).angle(new Station(-10, -10)));
//        System.out.println(new Station(0, 0).angle(new Station(0, -10)));
//        System.out.println(new Station(0, 0).angle(new Station(10, -10)));


        List<String> lines = IOUtils.getInputLines(Day10.class);
        Set<Station> stations = getStations(lines);

        int max = Integer.MIN_VALUE;
        Station base = stations.iterator().next();
        for (Station station : stations) {
            int visibleStation = getVisibleStations(station, stations);
            if (visibleStation > max) {
                base = station;
                max = visibleStation;
            }
        }

        System.out.println("BASE: " + base.x + ", " + (lines.size() -1 - base.y) + " Max="  + max);
        Station first = base;
        Map<Double, List<Station>> groupedByAngle = stations.stream()
                .filter(b -> !b.equals(first))
                .collect(Collectors.groupingBy(first::angle));

        var list = new ArrayList<>(groupedByAngle.values());
        list.sort(Comparator.comparingDouble(l -> {
            var angle = first.angle(l.get(0)) - 90;
            return angle < 0 ? angle + 360 : angle;
        }));
        Collections.reverse(list);
        list.forEach(l -> l.sort(Comparator.comparing(first::distance)));


        int i = list.size() - 1;
        int count = 0;
        while (true) {
            List<Station> sameAngle = list.get(i);
            i = (i + 1) % list.size();
            if (sameAngle.isEmpty()) {
                continue;
            }
            Station removed = sameAngle.remove(0);
            count++;
            if (count == 200) {
                System.out.println("LAST REMOVED: " + removed.x + ", " + (lines.size() - 1 - removed.y));
                break;
            }
        }

        System.out.println("DONE");
    }

    static int getVisibleStations(Station station, Set<Station> stations) {
        AtomicInteger count = new AtomicInteger();
        stations.forEach(s -> {
            if (station.equals(s)) return;
            if (isVisible(station, s, stations)) {
                count.incrementAndGet();
            }
        });
        return count.get();
    }

    static boolean isVisible(Station from, Station to, Set<Station> stations) {
        for (Station station : stations) {
            if (station.equals(from) || station.equals(to)) {
                continue;
            }
            if (isSingleLine(from, station, to)) {
                return false;
            }
        }
        return true;
    }

    static boolean isSingleLine(Station start, Station middle, Station end) {
        return start.distance(end) > start.distance(middle)
                && (start.angle(middle) == start.angle(end));
    }

    static Set<Station> getStations(List<String> lines) {
        var stations = new HashSet<Station>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                if (lines.get(y).charAt(x) == '#') {
                    stations.add(new Station(x, (lines.size() - 1 - y)));
                }
            }
        }
        return stations;
    }

    /**
     * [0, 0] - bottom left corner
     */
    record Station(int x, int y) {

        double angle(Station other) {
            int dx = other.x - x;
            int dy = other.y - y;
            double angle = Math.toDegrees(Math.atan2(dy, dx));
            if (angle < 0) {
                angle += 360;
            }
            return angle;
        }

        int distance(Station other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y);
        }
    }

}
