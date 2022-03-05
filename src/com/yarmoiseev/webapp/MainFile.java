package com.yarmoiseev.webapp;

import java.io.File;


public class MainFile {

    //Teeeeeest


    public static void main(String[] args) {
        final File folder = new File(".\\src");
        listFilesForFolder(folder, "");

    }

    private static void listFilesForFolder(File folder, String offset) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                System.out.println(offset + "+ " + fileEntry.getName());
                listFilesForFolder(fileEntry, offset + " ");
            } else {
                System.out.println(offset+ "   " + fileEntry.getName());
            }
        }
    }


}
