package com.cca.dashboard.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileStoreUtility {

    private static final String DIRECTORY_PATH = "C:/Users/Manjit/file_storage/";

    public FileStoreUtility() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void storeFile(byte[] file, String fileName) throws IOException {
        System.out.println(file);
        File outputFile = new File(DIRECTORY_PATH + fileName);
        System.out.println("before storing --> "+file.length);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(file);
        }
    }

    public byte[] getFile(String fileName) throws IOException {
        File inputFile = new File(DIRECTORY_PATH + fileName);
        if (!inputFile.exists()) {
            throw new IOException("File not found: " + fileName);
        }
        System.out.println("while recienving "+inputFile.length());
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byte[] fileBytes = new byte[(int) inputFile.length()];
            int bytesRead = fis.read(fileBytes);
            if (bytesRead < inputFile.length()) {
                throw new IOException("Could not read all bytes from file: " + fileName);
            }
            return fileBytes;
        }
    }
}
