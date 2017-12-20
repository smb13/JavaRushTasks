package com.javarush.task.task30.task3009;

import java.util.HashSet;
import java.util.Set;

/* 
Палиндром?
*/

public class Solution {
    private static Set<Integer> getRadix(String number) {
        Set<Integer> result = new HashSet<>();
        for (int i=2; i<=36; i++) {
            String s = "";
            try {
            s = Integer.toString(Integer.parseInt(number), i);
            if (isPolindrom(s)) result.add(i);
            } catch (NumberFormatException e) {

            }

        }
        return result;
    }

    private static boolean isPolindrom (String s) {
        StringBuffer sb = new StringBuffer(s);
        String s1 = sb.toString();
        String s2 = sb.reverse().toString();
        return s1.equals(s2);
    }

    public static void main(String[] args) {
        System.out.println(getRadix("112"));        //expected output: [3, 27, 13, 15]
        System.out.println(getRadix("123"));        //expected output: [6]
        System.out.println(getRadix("5321"));       //expected output: []
        System.out.println(getRadix("1A"));         //expected output: []
    }
}