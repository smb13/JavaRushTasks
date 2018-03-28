package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Map <String, ByteArrayOutputStream> entries = new HashMap<>();

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(args[1]));
        String destFileName = "new/" + Paths.get(args[0]).getFileName().toString();
        ByteArrayOutputStream baos;

        ZipEntry zipEntry;
        while((zipEntry = zipIn.getNextEntry()) != null) {
            baos = new ByteArrayOutputStream();
            if (zipEntry.getName().equals(destFileName)) {
                continue;
            }

            int n;
            byte[] buf = new byte[1024];
            while ((n = zipIn.read(buf)) > 0) {
                baos.write(buf, 0, n);
            }

            entries.put(zipEntry.getName(), baos);
            zipIn.closeEntry();
        }
        zipIn.close();

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(args[1]));
        zipOut.putNextEntry(new ZipEntry(destFileName));
        Path path = new File(args[0]).toPath();
        Files.copy(path, zipOut);
        zipOut.closeEntry();

        for (Map.Entry<String, ByteArrayOutputStream> entry: entries.entrySet()) {
            ByteArrayInputStream bais = new ByteArrayInputStream(entry.getValue().toByteArray());
            zipOut.putNextEntry(new ZipEntry(entry.getKey()));
            int n;
            byte[] buf = new byte[1024];
            while ((n = bais.read(buf)) > 0) {
                zipOut.write(buf, 0, n);
            }

            zipOut.closeEntry();
        }
        zipOut.close();

    }
}
