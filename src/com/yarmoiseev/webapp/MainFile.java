package com.yarmoiseev.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFile {
    private static List<File> files = new ArrayList<>();

    public static void main(String[] args) {
        getSubFiles(files, new File(".\\"));
        StringBuilder sb = new StringBuilder();
        for (Object file : files.toArray()) {
            if (((File) file).isDirectory()) {
                System.out.println(((File) file).getName() + sb.append("/"));
            } else {
                System.out.println(((File) file).getName());
            }
        }
    }

    private static void getSubFiles(List<File> source, File parent) {
        List<File> dir = new ArrayList<>();
        if (!source.contains(parent)) {
            source.add(parent);
        }
        File[] listFiles = parent.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File file : listFiles) {
            if (file.isDirectory()) {
                dir.add(file);
            } else {
                if (!source.contains(file)) {
                    source.add(file);
                }
            }
        }
        for (File file : dir) {
            getSubFiles(source, file);
        }

    }
}
