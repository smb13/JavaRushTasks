package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Mikhail Shamanin on 09.10.2017.
 */
public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
        else {
            if (f.getName().toLowerCase().endsWith(".html") || f.getName().toLowerCase().endsWith(".htm"))
                return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }

    /*public static void main (String[] args) {
        //String regex = ".+\\.[hH][tT][mM][lL]?";
        String regex = "(?i).+\\.html?";

        String fn = "hjgj.html";
        System.out.println(fn + " " + fn.matches(regex));
        fn = "hjgj.htm";
        System.out.println(fn + " " + fn.matches(regex));
        fn = "k.hTml";
        System.out.println(fn + " " + fn.matches(regex));
        fn = "hjgj.htk";
        System.out.println(fn + " " + fn.matches(regex));
        fn = "hjgjhtml";
        System.out.println(fn + " " + fn.matches(regex));
    }*/
}
