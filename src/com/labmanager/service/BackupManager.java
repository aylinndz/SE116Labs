package com.labmanager.service;

import java.io.IOException;
import java.nio.file.*;

public class BackupManager {

    public static boolean fileExists(Path path) {
        return Files.exists(path);
    }

    public static void copyInputFile(Path sourcePath, Path destinationPath) {
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Copy Error: " + e.getMessage());
        }
    }

    public static long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            System.err.println("Dimension Reading Error: " + e.getMessage());
            return -1;
        }
    }

    public static void appendLog(Path logPath, String message) {
        try {
            Files.writeString(logPath, message + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Log Writing Error: " + e.getMessage());
        }
    }
}