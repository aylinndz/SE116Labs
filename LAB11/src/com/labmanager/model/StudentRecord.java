package com.labmanager.model;

import java.io.Serializable;
public class StudentRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentID;
    private String name;
    private int completedLabs;
    private double averageScore;
    private transient String temporaryPassword;

    public StudentRecord(String studentID, String name, int completedLabs, double averageScore, String temporaryPassword) {
        this.studentID = studentID;
        this.name = name;
        this.completedLabs = completedLabs;
        this.averageScore = averageScore;
        this.temporaryPassword = temporaryPassword;
    }

    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCompletedLabs() {
        return completedLabs;
    }
    public void setCompletedLabs(int completedLabs) {
        this.completedLabs = completedLabs;
    }

    public double getAverageScore() {
        return averageScore;
    }
    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }
    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public boolean isSuccessful() {
        return (completedLabs >= 7 && averageScore >= 60.0);
    }

    public String getInfo() {
        return "ID: " + studentID + ", Name: " + name + ", Labs: " + completedLabs + ", Average: " + averageScore + ", Password: " + temporaryPassword;
    }


}
