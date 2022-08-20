package com.company.aoc2015;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Day22 {

    public static void main(String[] args) {
        List<Spell> spells = List.of(
                new Spell(53, 4, 0, 0, 0, 1),
                new Spell(73, 2, 0, 2, 0, 1),
                new Spell(113, 0, 7, 0, 0, 6),
                new Spell(173, 3, 0, 0, 0, 6),
                new Spell(229, 0, 0, 0, 101, 5));

        Boss boss = new Boss(55, 8);
        User user = new User(50, 500, 0, new ArrayList<>());

        // 840 - bad result part2 - 1309 - too hight
        if (play(user, boss, spells, Integer.MAX_VALUE)) {
            System.out.println(user.manaSpent);
        } else {
            System.out.println("Looser");
        }
    }

    static boolean play(User user, Boss boss, List<Spell> allSpells, int limit) {

        user.hitPoints--;
        if (user.hitPoints <= 0) return false;

        // begin of user's turn
        user.applySpellEffects(boss);
        if (boss.hitPoints <= 0) {
            return true;
        }

        // all spells that user may cast
        List<Spell> readyToCast = allSpells.stream()
                .filter(spell -> !user.activeSpells.contains(spell))
                .filter(spell -> spell.cost <= user.mana)
                .toList();

        // Try all available game paths (spells) that user may cast
        int minManaSpent = limit;
        for (Spell spell : readyToCast) {
            User clonedUser = user.cloneUser();
            Boss clonedBoss = boss.cloneBoss();
            clonedUser.castSpell(spell.cloneSpell(), clonedBoss);
            if (clonedUser.manaSpent >= limit) continue;

            // begin of bosses turn
            clonedUser.applySpellEffects(clonedBoss);
            if (clonedBoss.hitPoints <= 0) {
                minManaSpent = Math.min(minManaSpent, clonedUser.manaSpent);
                continue;
            }
            clonedUser.hitPoints -= Math.max(1, clonedBoss.damage - clonedUser.totalArmour());
            if (clonedUser.hitPoints <= 0) {
                continue;
            }

            if (play(clonedUser, clonedBoss, allSpells, minManaSpent)) {
                minManaSpent = Math.min(minManaSpent, clonedUser.manaSpent);
            }
        }
        if (minManaSpent == limit) {
            return false;
        } else {
            user.manaSpent = minManaSpent;
            return true;
        }
    }

    @AllArgsConstructor
    static class Boss {
        int hitPoints;
        int damage;

        public Boss cloneBoss() {
            return new Boss(hitPoints, damage);
        }
    }

    @AllArgsConstructor
    static class User {
        int hitPoints;
        int mana;
        int manaSpent;
        List<Spell> activeSpells;

        public User cloneUser() {
            User user = new User(hitPoints, mana, manaSpent, new ArrayList<>());
            user.activeSpells.addAll(this.activeSpells.stream().map(Spell::cloneSpell).toList());
            return user;
        }

        void castSpell(Spell spell, Boss boss) {
            mana -= spell.cost;
            manaSpent += spell.cost;
            if (spell.turns == 1) { // immediate spell effect
                spell.apply(this, boss);
            } else {
                activeSpells.add(spell);
            }
        }

        void applySpellEffects(Boss boss) {
            Iterator<Spell> iterator = activeSpells.iterator();
            while (iterator.hasNext()) {
                Spell spell = iterator.next();
                spell.apply(this, boss);
                if (spell.turns <= 0) iterator.remove();
            }
        }

        int totalArmour() {
            return activeSpells.stream().mapToInt(Spell::getArmor).sum();
        }
    }

    @Data
    @AllArgsConstructor
    static class Spell {
        int cost;
        int damage;
        int armor;
        int heal;
        int mana;
        int turns;

        void apply(User user, Boss boss) {
            boss.hitPoints -= this.damage;
            user.hitPoints += this.heal;
            user.mana += this.mana;
            this.turns--;
        }

        public Spell cloneSpell() {
            return new Spell(cost, damage, armor, heal, mana, turns);
        }

        public int hashCode() {
            return Objects.hashCode(cost);
        }

        public boolean equals(Object obj) {
            return cost == ((Spell) obj).cost;
        }
    }

}
