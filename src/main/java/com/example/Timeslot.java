package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Timeslot {

    private int timeslotid;
    private int tutorid;
    private int studentid;
    private String timeSlotTime; // e.g., "10:00 AM - 11:00 AM"

    public Timeslot(int timeslotid, int tutorid, int studentid, String timeSlotTime) {
        this.timeslotid = timeslotid;
        this.tutorid = tutorid;
        this.studentid = studentid;
        this.timeSlotTime = timeSlotTime;
    }

    // Getters and setters
    public int getTimeslotId() {
        return timeslotid;
    }

    public void setTimeslotId(int timeslotid) {
        this.timeslotid = timeslotid;
    }

    public int getTutorId() {
        return tutorid;
    }

    public void setTutorId(int tutorid) {
        this.tutorid = tutorid;
    }

    public int getStudentId() {
        return studentid;
    }

    public void setStudentId(int studentid) {
        this.studentid = studentid;
    }

    public String getTimeSlotTime() {
        return timeSlotTime;
    }

    public void setTimeSlotTime(String timeSlotTime) {
        this.timeSlotTime = timeSlotTime;
    }

    // Database interaction methods

    // Method to create a timeslot
    public static void createTimeslot(int tutorId, String timeSlotTime) throws SQLException {
        Connection connection = Main.connect();
        String query = "INSERT INTO timeslots (tutorId, timeSlotTime) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorId);
        statement.setString(2, timeSlotTime);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    // Method to assign a student to a timeslot
    public static void assignStudentToTimeslot(int timeslotid, int studentid) throws SQLException {
        Connection connection = Main.connect();
        String query = "UPDATE timeslot SET studentid = ? WHERE timeslotid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, studentid);
        statement.setInt(2, timeslotid);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    // Method to get timeslots for a specific tutor
    public static List<Timeslot> getTimeslotsByTutor(int tutorid) throws SQLException {
        Connection connection = Main.connect();
        String query = "SELECT * FROM timeslot WHERE tutorid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorid);
        ResultSet resultSet = statement.executeQuery();
        List<Timeslot> timeslots = new ArrayList<>();
        while (resultSet.next()) {
            int timeslotid = resultSet.getInt("timeslotid");
            int studentid = resultSet.getInt("studentid");
            String timeSlotTime = resultSet.getString("timeSlotTime");
            Timeslot timeslot = new Timeslot(timeslotid, tutorid, studentid, timeSlotTime);
            timeslots.add(timeslot);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return timeslots;
    }

    // Method to get timeslots for a specific student
    public static List<Timeslot> getTimeslotsByStudent(int studentid) throws SQLException {
        Connection connection = Main.connect();
        String query = "SELECT * FROM timeslot WHERE studentid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, studentid);
        ResultSet resultSet = statement.executeQuery();
        List<Timeslot> timeslots = new ArrayList<>();
        while (resultSet.next()) {
            int timeslotid = resultSet.getInt("timeslotid");
            int tutorid = resultSet.getInt("tutorid");
            String timeslotTime = resultSet.getString("timeslotTime");
            Timeslot timeslot = new Timeslot(timeslotid, tutorid, studentid, timeslotTime);
            timeslots.add(timeslot);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return timeslots;
    }
}

