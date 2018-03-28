package com.javarush.task.task32.task3213;

import java.io.*;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor Dpljr");
        System.out.println(decode(reader, -3));  //Hello Amigo

    }

    public static String decode(StringReader reader, int key) throws IOException {
        StringWriter writer = new StringWriter();
        if (reader != null) {
            int i;
            while ((i = reader.read()) > 0) {
                char chr = (char) i;
                writer.append((char) (chr + key));
            }
        }
        return writer.toString();
    }
}
