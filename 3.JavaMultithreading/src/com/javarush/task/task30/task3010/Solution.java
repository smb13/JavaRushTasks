package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

public class Solution {
    public static void main(String[] args) {
        try {
            String s = args[0].toLowerCase();
            //String s = "fgdi";
            int result = 0;
            char[] chrArr = s.toCharArray();
            int max = 0;
            for (char chr : chrArr) {
                if (!(((int) chr >= 48 && (int) chr <= 57) || ((int) chr >= 97 && (int) chr <= 122))) {
                    System.out.println("incorrect");
                    return;
                }
                if ((int) chr > max) max = (int) chr;
            }
            if (max <= 57) {
                result = 10 + max - 57;
                if (max == 48) result = 2;
            } else if (max <= 122) {
                result = 10 + max - 96;
            }
            System.out.println(result);
        } catch (Exception e) {
        }

    }
}
