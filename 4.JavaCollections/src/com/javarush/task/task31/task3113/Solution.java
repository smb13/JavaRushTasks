package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/*
Что внутри папки?
*/
public class Solution extends SimpleFileVisitor<Path> {
    private int numberOfFolders = 0;
    private int numberOfFiles = 0;
    private long size = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        Path path = Paths.get(str);
        if (!Files.isDirectory(path)) {
            System.out.println(path.toAbsolutePath().toString() + " - не папка");
            return;
        }
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        final Solution solution = new Solution();
        Files.walkFileTree(path, options, 20, solution);
        System.out.println("Всего папок - " + (solution.numberOfFolders - 1));
        System.out.println("Всего файлов - " + solution.numberOfFiles);
        System.out.println("Общий размер - " + solution.size);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        numberOfFiles++;
        size += Files.size(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        numberOfFolders++;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.SKIP_SUBTREE;
    }
}
