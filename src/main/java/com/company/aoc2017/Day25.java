package com.company.aoc2017;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day25 {

    public static void main(String[] args) {
        List<State> states = List.of(
                new State('A', new Action(1, Direction.RIGHT, 'B'), new Action(0, Direction.LEFT, 'B')),
                new State('B', new Action(1, Direction.LEFT, 'C'), new Action(0, Direction.RIGHT, 'E')),
                new State('C', new Action(1, Direction.RIGHT, 'E'), new Action(0, Direction.LEFT, 'D')),
                new State('D', new Action(1, Direction.LEFT, 'A'), new Action(1, Direction.LEFT, 'A')),
                new State('E', new Action(0, Direction.RIGHT, 'A'), new Action(0, Direction.RIGHT, 'F')),
                new State('F', new Action(1, Direction.RIGHT, 'E'), new Action(1, Direction.RIGHT, 'A'))
                );

        Map<Character, State> stateMap = states.stream().collect(Collectors.toMap(State::name, s -> s));

        Set<Integer> setIndexes = new HashSet<>();
        State state = stateMap.get('A');
        int index = 0;

        for (int i = 0; i < 12861455; i++) {

            int value = setIndexes.contains(index) ? 1 : 0;
            Action action = value == 0 ? state.action0() : state.action1();
            if (action.value == 1) {
                setIndexes.add(index);
            } else {
                setIndexes.remove(index);
            }
            index = index + (action.direction == Direction.LEFT ? -1 : 1);
            state = stateMap.get(action.nextState);
        }
        System.out.println(setIndexes.size());
    }

    record State(char name, Action action0, Action action1) {
    }

    record Action(int value, Direction direction, char nextState) {
    }

    enum Direction {LEFT, RIGHT}

    ;
}
