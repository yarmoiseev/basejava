package com.yarmoiseev.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFile {
    private static List<File> files = new ArrayList<>();

    public static void main(String[] args) {
        final File folder = new File(".\\");
        listFilesForFolder(folder);

    }

    private static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }


}
