package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Timeslot {

    private static int timeSlotid;
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
    public static void createTimeslot(int tutorId, int studentId, String timeSlotTime) {
        String query = "INSERT INTO timeslot (tutorid, studentid, timeSlotTime) VALUES (?, ?, ?)";
        
        try (Connection connection = Main.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            // Set the parameters
            statement.setInt(1, tutorId);
            statement.setInt(2, studentId);
            statement.setString(3, timeSlotTime);
            
            // Execute the update
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("aaa");
        }

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

    public String getTimeSlot(){
        return this.timeSlotTime;
    }
    

    public static ArrayList<Timeslot> searchReservedTimeslots(int targetId) throws SQLException {
		ArrayList<Timeslot> reservedHours = new ArrayList<Timeslot>();
		Connection connection = Main.connect();
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			String query = "select * from timeslot where tutorid like ?";
			stat = connection.prepareStatement(query);
			stat.setString(1, "%" + targetId + "%");
			stat.setString(2, "%" + targetId + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("timeslotid");
				int tutorid = rs.getInt("tutorid");
				int studentid = rs.getInt("studentid");
				String timeSlot = rs.getString("timeSlotTime");
				Timeslot newTimeslot = new Timeslot(id, tutorid, studentid, timeSlot);
				reservedHours.add(newTimeslot);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs != null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reservedHours;
	}
}

