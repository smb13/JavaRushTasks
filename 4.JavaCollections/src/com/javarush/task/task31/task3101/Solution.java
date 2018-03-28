package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Проход по дереву файлов
*/
public class Solution {
    static List<File> fileList = new ArrayList<File>();

    public static void main(String[] args) {
        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        //File path = new File("d:\\tmp");
        //File resultFileAbsolutePath = new File("d:\\temp\\all.txt");
        File newFile = new File(resultFileAbsolutePath.getParent() + "\\allFilesContent.txt");
        FileUtils.renameFile(resultFileAbsolutePath, newFile);
        try (FileOutputStream writer = new FileOutputStream(newFile)) {
            if (!(path.isDirectory()) && newFile.isFile()) {
                return;
            }

            fillFileList(path.getPath());
            fileList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
            for (File ff: fileList) {
                FileInputStream reader = new FileInputStream(ff);
                while (reader.available() > 0) {
                    writer.write(reader.read());
                }
                writer.write(System.lineSeparator().getBytes());
                writer.flush();
                reader.close();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fillFileList(String path) {
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                fillFileList(file.getAbsolutePath());
                continue;
            }
            if (file.length() > 50)
                FileUtils.deleteFile(file);
            else
                fileList.add(file);
        }
    }
}
