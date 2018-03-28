 package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

 /*
 Генератор паролей
 */
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        char[] lowerCaseLetters = new char[26];
        char[] upperCaseLetters = new char[26];
        char[] digits = new char[10];
        for (int i=0; i<26; i++) {
            lowerCaseLetters[i] = (char) ('a' + i);
            upperCaseLetters[i] = (char) ('A' + i);
            if (i<10){
                digits[i] = (char) ('0' + i);
            }
        }
        List<Character> password = new ArrayList<>();
        int rand = (int)(Math.random()*26);
        password.add(lowerCaseLetters[rand]);
        rand = (int)(Math.random()*26);
        password.add(upperCaseLetters[rand]);
        rand = (int)(Math.random()*10);
        password.add(digits[rand]);
        for (int i=0; i<5; i++) {
            int type = (int)(Math.random()*3);
            switch (type) {
                case 0 : rand = (int)(Math.random()*26);
                    password.add(lowerCaseLetters[rand]);
                    break;
                case 1 : rand = (int)(Math.random()*26);
                    password.add(upperCaseLetters[rand]);
                    break;
                case 2 :rand = (int)(Math.random()*10);
                    password.add(digits[rand]);
            }
        }
        Collections.shuffle(password);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        char[] pwdArray = new char[8];
        for (int i=0; i<8; i++) pwdArray[i] = password.get(i);
        try {
            baos.write(new String(pwdArray).getBytes());
        } catch (Exception e) {

        }
        return baos;
    }
}