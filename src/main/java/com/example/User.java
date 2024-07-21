package com.example;
import java.sql.*;
import java.util.List;


public abstract class User {

    protected int id;
    protected String username;
    protected String email;
    protected String password;
    protected String biography;
    protected String userType;

    private static User currentUser;
    public static User profileUser;

    public User(int id, String username, String password, String email, String biography) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.biography = biography;
    }

    // Getters and setters
    public static User getCurrentUser() {
        return currentUser;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    // Method to get the account type
    public abstract String getAccountType();

    public static boolean isValidEmail(String email) {
        if (email.lastIndexOf('@') == -1 || email.lastIndexOf('.') == -1) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < email.length(); ++i) {
            if (email.charAt(i) == '@') {
                count++;
            }
        }
        if (count > 1 || count == 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidUsername(String username) {
        return username.trim().equals(username) && username.length() >= 3 && username.toLowerCase().equals(username);
    }

    public static boolean isValidPassword(String password) {
        if (password.length() <= 8 || password.toLowerCase().equals(password)) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) < '/' && password.charAt(i) > ':') {
                count++;
            }
        }
        if (count == password.length()) {
            return false;
        }
        count = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) < '0' && password.charAt(i) > ' ') {
                count++;
            }
        }
        return count != password.length();
    }

    public static boolean isPasswordConfirmed(String enterPassword, String confirmPassword) {
        return enterPassword.equals(confirmPassword);
    }

     public void createTimeslot(String timeslotTime) throws SQLException {
        if ("tutor".equals(userType)) {
            Timeslot.createTimeslot(this.id, timeslotTime);
        } else {
            throw new SQLException("Only tutors can create timeslots");
        }
    }

    public void assignStudentToTimeslot(int timeslotId, int studentId) throws SQLException {
        if ("tutor".equals(userType)) {
            Timeslot.assignStudentToTimeslot(timeslotId, studentId);
        } else {
            throw new SQLException("Only tutors can assign students to timeslots");
        }
    }

    public List<Timeslot> getSchedule() throws SQLException {
        if ("tutor".equals(userType)) {
            return Timeslot.getTimeslotsByTutor(this.id);
        } else {
            throw new SQLException("Only tutors can view their schedule");
        }
    }

    // Methods for viewing timeslots as a student
    public List<Timeslot> getTimeslots() throws SQLException {
        if ("student".equals(userType)) {
            return Timeslot.getTimeslotsByStudent(this.id);
        } else {
            throw new SQLException("Only students can view their timeslots");
        }
    }

    public void logOut(){
        setCurrentUser(null);
    }
}
