package com.javarush.task.task30.task3002;

/* 
Осваиваем методы класса Integer
*/
public class Solution {

    public static void main(String[] args) {
        System.out.println(convertToDecimalSystem("0x16")); //22
        System.out.println(convertToDecimalSystem("012"));  //10
        System.out.println(convertToDecimalSystem("0b10")); //2
        System.out.println(convertToDecimalSystem("62"));   //62
    }

    public static String convertToDecimalSystem(String s) {
        String result = "";
        if ((s.length() >= 2) && s.substring(0, 1).equals("0")) {
            if (s.substring(1, 2).getBytes()[0] > 57 || s.substring(1, 2).getBytes()[0] < 48) {
                switch (s.substring(0, 2).toLowerCase()) {
                    case "0x":
                        result = Integer.toString(Integer.parseInt(s.substring(2, s.length()), 16));
                        break;
                    case "0b":
                        result = Integer.toString(Integer.parseInt(s.substring(2, s.length()), 2));
                        break;
                }
            } else {
                //result = Integer.toString(Integer.parseInt(s.substring(1, s.length()), 8), 8);
                result = Integer.toString(Integer.parseInt(s, 8));
            }
        } else {
            result = Integer.toString(Integer.parseInt(s));
        }
        return result;
    }
}
