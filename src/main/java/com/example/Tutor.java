package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Tutor extends User {
    private List<Timeslot> schedule;

    public Tutor(int id, String username, String password, String email) {
        super(id, username, password, email);
        this.schedule = new ArrayList<>();
    }

    @Override
    public String getAccountType() {
        return "tutor";
    }

    public static User logIn(String username, String password) throws SQLException {

		ResultSet r = getByUsername(username);
		if (r.next() && r.getString("password").equals(password)) {

			int id = r.getInt("tutorid");

			String email = r.getString("email");
            Tutor newTutor = new Tutor(id, username, email, password);
            User.setCurrentUser(newTutor);
			return newTutor;

		}

		r.close();
		welcomePage.showInvalidLoginError();
		return null;

	}

    public static ResultSet getByUsername(String username) throws SQLException {

		Connection connection = Main.connect();
		String query = "select * from tutor where name = ?";
		PreparedStatement stat = connection.prepareStatement(query);
		stat.setString(1, username);
		ResultSet r = stat.executeQuery();

		return r;

	}
    public List<Timeslot> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Timeslot> schedule) {
        this.schedule = schedule;
    }

    // Method to create a timeslot
    public void createTimeslot(String timeslotTime) throws SQLException {
        Timeslot.createTimeslot(this.getId(), timeslotTime);
    }

    // Method to assign a student to a timeslot
    public void assignStudentToTimeslot(int timeslotId, int studentId) throws SQLException {
        Timeslot.assignStudentToTimeslot(timeslotId, studentId);
    }

    // Method to load schedule from database
    public void loadSchedule() throws SQLException {
        this.schedule = Timeslot.getTimeslotsByTutor(this.getId());
    }

    public static Tutor getById(int tutorid) throws SQLException {
        Connection connection = Main.connect();
        String query = "SELECT * FROM tutor WHERE tutorid = ?";
        PreparedStatement stat = connection.prepareStatement(query);
        stat.setInt(1, tutorid);
        ResultSet r = stat.executeQuery();
        if (r.next()) {
            String username = r.getString("name");
            String password = r.getString("password");
            String email = r.getString("email");

            stat.close();
            connection.close();
            return new Tutor(tutorid, username, password, email);
        }
        stat.close();
        connection.close();
        return null;
    }

    public static Tutor signUp(String username, String password, String email) throws SQLException {
        Connection connection = DriverManager.getConnection(Main.getMySqlUrl(), Main.getMySqlUsername(), Main.getMySqlPassword());
        Statement idStatement = connection.createStatement();
        ResultSet r = idStatement.executeQuery("SELECT tutorid FROM tutor ORDER BY tutorid DESC");
        r.next();
        int id = r.getInt(1) + 1;
        idStatement.close();
        connection.close();

        if (!isValidUsername(username)) {
            welcomePage.showInvalidUsernameError();
            return null;
        }
        if (!isUsernameUnique(username, "tutor", "tutorid")) {
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

        return addTeacher(id, username, password, email);
    }

    public static boolean isUsernameUnique(String username, String table, String column) throws SQLException {
        Connection connection = Main.connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table + " WHERE name='" + username + "';");
        if (resultSet.next()) {
            return false;
        }
        statement.close();
        connection.close();
        return true;
    }

    public static Tutor addTeacher(int tutorid, String name, String password, String email) throws SQLException {
        Connection connection = DriverManager.getConnection(Main.getMySqlUrl(), Main.getMySqlUsername(), Main.getMySqlPassword());
        Tutor teacher = new Tutor(tutorid, name, password, email);
        String query = "INSERT INTO tutor (tutorid, name, password, email) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorid);
        statement.setString(2, name);
        statement.setString(3, password);
        statement.setString(4, email);

        statement.executeUpdate();
        statement.close();
        connection.close();
        return new Tutor(tutorid, name, password, email);
    }
}