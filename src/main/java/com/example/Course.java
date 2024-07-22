package com.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseid;
    private String coursename;
    private int subjectid;

    public Course(int courseid, String coursename, int subjectid) {
        this.courseid = courseid;
        this.coursename = coursename;
        this.subjectid = subjectid;
    }

    // Getters and setters
    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getSubjectId() {
        return subjectid;
    }

    public void setSubjectId(int subjectid) {
        this.subjectid = subjectid;
    }

    // Fetch courses by subjectId
    public static List<Course> getCoursesBySubject(int subjectId) throws SQLException {
        Connection connection = Main.connect();
        String query = "SELECT * FROM courses WHERE subjectid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, subjectId);
        ResultSet resultSet = statement.executeQuery();

        List<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            int courseId = resultSet.getInt("courseid");
            String courseName = resultSet.getString("coursename");
            courses.add(new Course(courseId, courseName, subjectId));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return courses;
    }

    // Fetch all courses for a tutor
    public static List<Course> getCoursesByTutor(int tutorId) throws SQLException {
        Connection connection = Main.connect();
        String query = "SELECT c.* FROM courses c JOIN tutor_courses tc ON c.courseId = tc.courseId WHERE tc.tutorId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorId);
        ResultSet resultSet = statement.executeQuery();

        List<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            int courseId = resultSet.getInt("courseid");
            String courseName = resultSet.getString("coursename");
            int subjectId = resultSet.getInt("subjectid");
            courses.add(new Course(courseId, courseName, subjectId));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return courses;
    }

    // Assign a course to a tutor
    public static void assignCourseToTutor(int tutorid, int courseid) throws SQLException {
        Connection connection = Main.connect();
        String query = "INSERT INTO tutor_courses (tutorid, courseid) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorid);
        statement.setInt(2, courseid);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}
