package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(args[0], "rw")) {
            long position = Long.parseLong(args[1]);
            String textForComparing = args[2];
            int lenOfStr = textForComparing.getBytes().length;
            //System.out.println(Arrays.toString(textForComparing.getBytes()));
            if ((position + lenOfStr) <= randomAccessFile.length()) {
                randomAccessFile.seek(position);
                byte[] buffer = new byte[lenOfStr];
                randomAccessFile.read(buffer, 0, lenOfStr);
                //System.out.println(Arrays.toString(buffer));
                randomAccessFile.seek(randomAccessFile.length());
                String strFromFile = new String(buffer);
                if (textForComparing.equals(strFromFile)) {
                    randomAccessFile.write("true".getBytes());
                } else {
                    randomAccessFile.write("false".getBytes());
                }
            } else {
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write("false".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
