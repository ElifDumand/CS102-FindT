package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
public class Student extends User {

    public Student(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    @Override
    public String getAccountType() {
        return "Student";
    }

    public List<Timeslot> getTimeslots() throws SQLException {
        return Timeslot.getTimeslotsByStudent(this.getId());
    }

    public static Student getById(int studentid) throws SQLException {
        Connection connection = Main.connect();
        String query = "SELECT * FROM student WHERE studentid = ?";
        PreparedStatement stat = connection.prepareStatement(query);
        stat.setInt(1, studentid);
        ResultSet r = stat.executeQuery();
        if (r.next()) {
            String username = r.getString("username");
            String password = r.getString("password");
            String email = r.getString("email");

            stat.close();
            connection.close();
            return new Student(studentid, username, password, email);
        }
        stat.close();
        connection.close();
        return null;
    }

    public static Student signUp(String username, String password, String email) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(Main.getMySqlUrl(), Main.getMySqlUsername(), Main.getMySqlPassword());
        Statement idStatement = connection.createStatement();
        ResultSet r = idStatement.executeQuery("SELECT studentid FROM student ORDER BY studentid DESC");
        r.next();
        int id = r.getInt(1) + 1;
        idStatement.close();
        connection.close();
        scanner.close();

        if (!isValidUsername(username)) {
            welcomePage.showInvalidUsernameError();
            return null;
        }
        if (!isUsernameUnique(username, "student", "studentid")) {
            welcomePage.showNotUniqueUsernameError();
            return null;
        }
        if (!isValidPassword(password)) {
            welcomePage.showInvalidPasswordError();
            return null;
        }
        if (!isValidEmail(email)) {
            welcomePage.showInvalidEmailError();
            return null;
        }

        return addStudent(id, username, password, email); 

    }

    public static User logIn(String username, String password) throws SQLException {

		ResultSet r = getByUsername(username);
		if (r.next() && r.getString("password").equals(password)) {

			int id = r.getInt("studentid");

			String email = r.getString("email");
            Student newStudent = new Student(id, username, password, email);
            User.setCurrentUser(newStudent);
			return newStudent;

		}

		r.close();
		welcomePage.showInvalidLoginError();
		return null;

	}

    public static ResultSet getByUsername(String username) throws SQLException {

		Connection connection = Main.connect();
		String query = "select * from student where name = ?";
		PreparedStatement stat = connection.prepareStatement(query);
		stat.setString(1, username);
		ResultSet r = stat.executeQuery();

		return r;

	}

    public static boolean isUsernameUnique(String name, String table, String column) throws SQLException {
        Connection connection = Main.connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table + " WHERE name='" + name + "';");
        if (resultSet.next()) {
            return false;
        }
        statement.close();
        connection.close();
        return true;
    }

    public static Student addStudent(int studentid, String name, String password, String email) throws SQLException {
        Connection connection = DriverManager.getConnection(Main.getMySqlUrl(), Main.getMySqlUsername(), Main.getMySqlPassword());
        Student student = new Student(studentid, name, password, email);
        String query = "INSERT INTO student (studentid, name, password, email) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, studentid);
        statement.setString(2, name);
        statement.setString(3, password);
        statement.setString(4, email);
        
        statement.executeUpdate();
        statement.close();
        connection.close();
        return new Student(studentid, name, password, email);
    }
}
