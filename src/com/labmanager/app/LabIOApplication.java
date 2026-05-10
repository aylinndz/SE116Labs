package com.labmanager.app;
import com.labmanager.model.StudentRecord;
import com.labmanager.service.RecordManager;
import com.labmanager.service.BackupManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LabIOApplication {

    public static void main(String[] args) {
        Path inputPath = Paths.get("data", "students.txt");
        Path outputDir = Paths.get("output");
        Path successfulReportPath = Paths.get("output", "successful_students.txt");
        Path summaryPath = Paths.get("output", "summary.txt");
        Path textBackupPath = Paths.get("output", "students_backup.txt");
        Path objectBackupPath = Paths.get("output", "student_records.ser");
        Path logPath = Paths.get("output", "log.txt");

        if (!BackupManager.fileExists(inputPath)) {
            System.out.println("Input file not found: " + inputPath);
            return;
        }

        try {
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            BackupManager.appendLog(logPath, "Program started");

            ArrayList<StudentRecord> records = RecordManager.readRecords(inputPath);

            System.out.println(" Loaded Records: ");
            for (int i = 0; i < records.size(); i++) {
                System.out.println(records.get(i).getInfo());
            }

            RecordManager.writeSuccessfulReport(records, successfulReportPath);
            RecordManager.writeSummaryReport(records, summaryPath);

            BackupManager.appendLog(logPath, "Reports created");

            BackupManager.copyInputFile(inputPath, textBackupPath);

            long size = BackupManager.getFileSize(textBackupPath);
            System.out.println(" Backup file size: " + size + " bytes");

            RecordManager.serializeRecords(records, objectBackupPath);

            ArrayList<StudentRecord> loadedRecords = RecordManager.deserializeRecords(objectBackupPath);

            System.out.println("Deserialized Records (Transient Check): ");
            for (int i = 0; i < loadedRecords.size(); i++) {
                System.out.println(loadedRecords.get(i).getInfo());
            }

            BackupManager.appendLog(logPath, "Program finished");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}