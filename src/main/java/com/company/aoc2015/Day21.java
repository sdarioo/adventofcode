package com.company.aoc2015;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day21 {

    public static void main(String[] args) {


        List<Item> weapons = List.of(
                new Item(8, 4, 0),
                new Item(10, 5, 0),
                new Item(25, 6, 0),
                new Item(40, 7, 0),
                new Item(74, 8, 0));

        List<Item> armors = List.of(
                new Item(0, 0, 0),
                new Item(13, 0, 1),
                new Item(31, 0, 2),
                new Item(53, 0, 3),
                new Item(75, 0, 4),
                new Item(102, 0, 5));

        List<Item> rings = List.of(
                new Item(0, 0, 0),
                new Item(0, 0, 0),
                new Item(25, 1, 0),
                new Item(50, 2, 0),
                new Item(100, 3, 0),
                new Item(20, 0, 1),
                new Item(40, 0, 2),
                new Item(80, 0, 3));

        AtomicInteger minCost = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger maxCost = new AtomicInteger(Integer.MIN_VALUE);

        weapons.forEach(weapon -> {
            armors.forEach(armor -> {
                for (int i = 0; i < rings.size() - 1; i++) {
                    Item ring1 = rings.get(i);
                    for (int j = i + 1; j < rings.size(); j++) {
                        Item ring2 = rings.get(j);
                        User boss = new User(104, 8, 1);
                        User me = new User(100,
                                weapon.damage + ring1.damage + ring2.damage,
                                armor.armour + ring1.armour + ring2.armour);
                        int cost = weapon.cost + armor.cost + ring1.cost + ring2.cost;
                        if (fight(boss, me)) {
                            minCost.set(Math.min(minCost.get(), cost));
                        } else {
                            maxCost.set(Math.max(maxCost.get(), cost));
                        }
                    }
                }
            });
        });
        System.out.println("Min cost: " + minCost.get());
        System.out.println("Max cost: " + maxCost.get());
    }

    static boolean fight(User boss, User me) {
        while (boss.hitPoints > 0 && me.hitPoints > 0) {
            boss.hitPoints -= Math.max(1, me.damage - boss.armor);
            me.hitPoints -= Math.max(1, boss.damage - me.armor);
        }
        return boss.hitPoints <= 0;
    }

    @AllArgsConstructor
    static class User {
        int hitPoints;
        int damage;
        int armor;
    }

    record Item(int cost, int damage, int armour) {
    }
}
