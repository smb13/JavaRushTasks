package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
Разархивируем многотомный архив
*/
public class Solution {
    public static void main(String[] args) {
        String resultFN = args[0];
        List<String> fileNames = new ArrayList<>();

        for (int i = 1; i < args.length; i++) {
            fileNames.add(args[i]);
        }
        Collections.sort(fileNames);

        List<FileInputStream> fisList = new ArrayList<>();
        try {
            for (int i = 0; i < fileNames.size(); i++) {
                fisList.add(new FileInputStream(fileNames.get(i)));
            }
            SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(fisList));
            ZipInputStream zipIS = new ZipInputStream (sis);
            FileOutputStream resultFOS = new FileOutputStream(resultFN);
            byte[] buffer = new byte[1024];
            int n;
            ZipEntry zipEntry;
            while ((zipEntry = zipIS.getNextEntry()) != null) {
                while ((n = zipIS.read(buffer)) > 0) {
                    resultFOS.write(buffer, 0, n);
                }
                zipIS.closeEntry();
            }
            sis.close();
            zipIS.close();
            resultFOS.close();
        } catch (Exception e) {
        }
    }
}
