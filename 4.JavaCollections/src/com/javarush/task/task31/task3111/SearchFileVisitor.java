package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize;
    private int maxSize;
    private List<Path> foundFiles = new ArrayList<>();

    public SearchFileVisitor() {
        this.partOfName = "";
        this.partOfContent = "";
        this.minSize = 0;
        this.maxSize = 0;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (!attrs.isRegularFile()) return CONTINUE;

        if (!this.partOfName.equals("") && file.getFileName().toString().indexOf(this.partOfName) == -1)
            return CONTINUE;

        if (this.minSize > 0 && attrs.size() < minSize)
            return CONTINUE;

        if (this.maxSize > 0 && attrs.size() > maxSize)
            return CONTINUE;

        if (!this.partOfContent.equals("") && new String(Files.readAllBytes(file)).indexOf(partOfContent) == -1)
            return CONTINUE;

        foundFiles.add(file);

        return CONTINUE;
    }
}
