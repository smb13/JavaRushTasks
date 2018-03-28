package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> result = new ArrayList<>();
        Queue<String> paths = new LinkedList<String>();
        paths.add(root);
        while (!paths.isEmpty()) {
            File path = new File(paths.poll());
            for(File file: path.listFiles()) {
                if (file.isDirectory()) {
                    paths.add(file.getAbsolutePath());
                } else {
                    result.add(file.getAbsolutePath());
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        List<String> list  = getFileTree("D:\\JavaProjects");
    }
}
