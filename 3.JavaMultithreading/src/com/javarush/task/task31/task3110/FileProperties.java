package com.javarush.task.task31.task3110;

/**
 * Created by Mikhail Shamanin on 29.07.2017.
 */
public class FileProperties {
    private String name;
    private long size;
    private long compressedSize;
    private int compressionMethod;

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public int getCompressionMethod() {
        return compressionMethod;
    }

    public FileProperties(String name, long size, long compressedSize, int compressionMethod) {
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
        this.compressionMethod = compressionMethod;
    }

    public long getCompressionRatio(){
        return (100 - ((compressedSize * 100) / size));
    }


    @Override
    public String toString() {
        if (size == 0) {
            return name;
        } else {
            return String.format("%s %d Kb (%d Kb) сжатие:%d%s", name, size / 1024, compressedSize / 1024, getCompressionRatio(), "%");
        }
    }
}
