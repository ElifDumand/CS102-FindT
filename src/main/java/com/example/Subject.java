package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Subject {
    private int subjectid;
    private String subjectname;

    public Subject(int subjectId, String subjectname) {
        this.subjectid = subjectId;
        this.subjectname = subjectname;
    }

    // Getters and setters
    public int getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    // Fetch all subjects from the database
    public static List<Subject> getAllSubjects() throws SQLException {
        Connection connection = Main.connect();
        String query = "SELECT * FROM subject";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Subject> subjects = new ArrayList<>();
        while (resultSet.next()) {
            int subjectid = resultSet.getInt("subjectid");
            String subjectname = resultSet.getString("subjectname");
            subjects.add(new Subject(subjectid, subjectname));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return subjects;
    }
}