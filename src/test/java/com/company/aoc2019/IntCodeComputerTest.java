package com.company.aoc2019;

import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class IntCodeComputerTest {

    @Test
    public void outputSameAsInput() {
        IntCodeComputer computer = new IntCodeComputer("3,0,4,0,99");
        assertThat(computer.computeNext(1)).hasValue(BigInteger.ONE);
    }

    @Test
    public void testDay5bExamples() {
        String input = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";

        assertThat(new IntCodeComputer(input).computeNext(7)).hasValue(BigInteger.valueOf(999));
        assertThat(new IntCodeComputer(input).computeNext(8)).hasValue(BigInteger.valueOf(1000));
        assertThat(new IntCodeComputer(input).computeNext(9)).hasValue(BigInteger.valueOf(1001));

        input = "3,9,8,9,10,9,4,9,99,-1,8";
        assertThat(new IntCodeComputer(input).computeNext(8)).hasValue(BigInteger.valueOf(1));
        assertThat(new IntCodeComputer(input).computeNext(1)).hasValue(BigInteger.valueOf(0));

        input = "3,9,7,9,10,9,4,9,99,-1,8";
        assertThat(new IntCodeComputer(input).computeNext(7)).hasValue(BigInteger.valueOf(1));
        assertThat(new IntCodeComputer(input).computeNext(8)).hasValue(BigInteger.valueOf(0));

        input = "3,3,1108,-1,8,3,4,3,99";
        assertThat(new IntCodeComputer(input).computeNext(7)).hasValue(BigInteger.valueOf(0));
        assertThat(new IntCodeComputer(input).computeNext(8)).hasValue(BigInteger.valueOf(1));

        input = "3,3,1107,-1,8,3,4,3,99";
        assertThat(new IntCodeComputer(input).computeNext(7)).hasValue(BigInteger.valueOf(1));
        assertThat(new IntCodeComputer(input).computeNext(8)).hasValue(BigInteger.valueOf(0));

        input = "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9";
        assertThat(new IntCodeComputer(input).computeNext(0)).hasValue(BigInteger.valueOf(0));
        assertThat(new IntCodeComputer(input).computeNext(1)).hasValue(BigInteger.valueOf(1));

        input = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1";
        assertThat(new IntCodeComputer(input).computeNext(0)).hasValue(BigInteger.valueOf(0));
        assertThat(new IntCodeComputer(input).computeNext(1)).hasValue(BigInteger.valueOf(1));
    }

    @Test
    public void testDay5a() throws IOException {
        String path = "C:\\Devel\\coding-challange\\src\\main\\java\\com\\company\\aoc2019\\input\\day5.txt";
        String input = Files.readString(Path.of(path)).trim();

        IntCodeComputer computer = new IntCodeComputer(input);
        assertThat(computer.computeAll(1).stream()
                .filter(v -> !v.equals(BigInteger.ZERO)).toList()).containsExactly(BigInteger.valueOf(16209841));
    }

    @Test
    public void testDay5b() throws IOException {
        String path = "C:\\Devel\\coding-challange\\src\\main\\java\\com\\company\\aoc2019\\input\\day5.txt";
        String input = Files.readString(Path.of(path)).trim();

        IntCodeComputer computer = new IntCodeComputer(input);
        assertThat(computer.computeNext(5)).hasValue(BigInteger.valueOf(8834787L));
    }

    @Test
    public void testDay9aExample() {
        String input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        assertThat(new IntCodeComputer(input).computeAll(new int[0]).stream()
                .map(BigInteger::toString).collect(Collectors.joining(","))).isEqualTo(input);

        input = "1102,34915192,34915192,7,4,7,99,0";
        assertThat(new IntCodeComputer(input).computeNext(new int[0])).hasValue(new BigInteger("1219070632396864"));

        input = "104,1125899906842624,99";
        assertThat(new IntCodeComputer(input).computeNext(new int[0])).hasValue(new BigInteger("1125899906842624"));
    }

    @Test
    public void testDay9a() throws IOException {
        String path = "C:\\Devel\\coding-challange\\src\\main\\java\\com\\company\\aoc2019\\input\\day9.txt";
        String input = Files.readString(Path.of(path)).trim();
        assertThat(new IntCodeComputer(input).computeNext(new int[]{1})).hasValue(new BigInteger("2399197539"));
    }

    @Test
    public void testDay9b() throws IOException {
        String path = "C:\\Devel\\coding-challange\\src\\main\\java\\com\\company\\aoc2019\\input\\day9.txt";
        String input = Files.readString(Path.of(path)).trim();
        assertThat(new IntCodeComputer(input).computeNext(new int[]{2})).hasValue(new BigInteger("35106"));
    }

}
