package com.labmanager.service;

import com.labmanager.model.StudentRecord;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class RecordManager {

    public static ArrayList<StudentRecord> readRecords(Path inputPath) {
        ArrayList<StudentRecord> records = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 5) {
                    System.out.println("Missing fields in line: " + line);
                    continue;
                }

                try {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    int labs = Integer.parseInt(parts[2].trim());
                    double score = Double.parseDouble(parts[3].trim());
                    String pass = parts[4].trim();

                    records.add(new StudentRecord(id, name, labs, score, pass));
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Invalid numeric value in line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }

        return records;
    }

    public static void writeSuccessfulReport(ArrayList<StudentRecord> records, Path reportPath) {
        try (BufferedWriter writer = Files.newBufferedWriter(reportPath)) {
            writer.write("Successful Students: ");

            for (int i = 0; i < records.size(); i++) {
                StudentRecord r = records.get(i);
                if (r.isSuccessful()) {
                    writer.write(r.getInfo() + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }

    public static void writeSummaryReport(ArrayList<StudentRecord> records, Path summaryPath) {
        int total = records.size();
        int successful = 0;
        double sumScore = 0;

        for (int i = 0; i < records.size(); i++) {
            StudentRecord r = records.get(i);
            if (r.isSuccessful()) {
                successful++;
            }
            sumScore += r.getAverageScore();
        }

        int unsuccessful = total - successful;

        double classAverage;
        if (total > 0) {
            classAverage = sumScore / total;
        } else {
            classAverage = 0.0;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(summaryPath)) {
            writer.write("Total Students: " + total );
            writer.write("Successful Students: " + successful );
            writer.write("Unsuccessful Students: " + unsuccessful );
            writer.write("Class Average Score: " + classAverage);
        } catch (IOException e) {
            System.err.println("Error writing summary: " + e.getMessage());
        }
    }

    public static void serializeRecords(ArrayList<StudentRecord> records, Path backupPath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(backupPath))) {
            oos.writeObject(records);
        } catch (IOException e) {
            System.err.println("Serialization Error: " + e.getMessage());
        }
    }

    public static ArrayList<StudentRecord> deserializeRecords(Path backupPath) {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(backupPath))) {
            return (ArrayList<StudentRecord>) ois.readObject();
        } catch (IOException e) {
            System.err.println("File Read Error: " + e.getMessage());
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.err.println("“Class Not Found” Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}