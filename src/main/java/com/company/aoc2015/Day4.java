package com.company.aoc2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String input = "yzbqklnj";
        int i = 1;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        while (true) {
            byte[] digest = md5.digest((input + i).getBytes());
            if (bytesToHex(digest).startsWith("000000")) {
                break;
            }
            i++;
        }
        System.out.println(i);
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
