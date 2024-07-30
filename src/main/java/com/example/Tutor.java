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
    private String university;
    private int price;

    static Tutor currentReceiver = null;

    public Tutor(int id, String username, String password, String email, int price, String university) {
        super(id, username, password, email);
        this.schedule = new ArrayList<>();
        this.university = university;
        this.price = price;
    }

    @Override
    public String getAccountType() {
        return "tutor";
    }

    public static Tutor logIn(String name, String password) throws SQLException {

		ResultSet r = getByUsername(name);
		if (r.next() && r.getString("password").equals(password)) {

			int tutorid = r.getInt("tutorid");

			String email = r.getString("email");
            String university = r.getString("university");
            int price = r.getInt("price");
            Tutor newTutor = new Tutor(tutorid, name, password, email, price, university );
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
        Timeslot.createTimeslot(this.getId(), -1, timeslotTime);
    }

    // Method to assign a student to a timeslot
    public void assignStudentToTimeslot(int timeslotId, int studentId) throws SQLException {
        Timeslot.assignStudentToTimeslot(timeslotId, studentId);
    }

    // Method to load schedule from database
    public void loadSchedule() throws SQLException {
        this.schedule = Timeslot.getTimeslotsByTutor(this.getId());
    }

    public static User getById(int tutorid){
        try (Connection connection = Main.connect()) {
            String query = "SELECT * FROM tutor WHERE tutorid = ?";
            PreparedStatement stat = connection.prepareStatement(query);
            try {
                stat.setInt(1, tutorid);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ResultSet r = stat.executeQuery();
            if (r.next()) {
                String username = r.getString("name");
                String password = r.getString("password");
                String email = r.getString("email");
                String university = r.getString("university");
                int price = r.getInt("price");

                stat.close();
                connection.close();
                return new Tutor(tutorid, username, password, email, price, university);
            }
            stat.close();
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Tutor signUp(String username, String password, String email, int price, String university) throws SQLException {
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

        return addTeacher(id, username, password, email, price, university);
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

    public static Tutor addTeacher(int tutorid, String name, String password, String email, int price, String university) throws SQLException {
        Connection connection = DriverManager.getConnection(Main.getMySqlUrl(), Main.getMySqlUsername(), Main.getMySqlPassword());
        Tutor teacher = new Tutor(tutorid, name, password, email, price, university);
        String query = "INSERT INTO tutor (tutorid, name, password, email) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorid);
        statement.setString(2, name);
        statement.setString(3, password);
        statement.setString(4, email);
        statement.setInt(5, price );
        statement.setString(6, university);

        statement.executeUpdate();
        statement.close();
        connection.close();
        return new Tutor(tutorid, name, password, email, price, university);
    }

    public static List<Tutor> getAllTutors() throws SQLException {
        List<Tutor> tutors = new ArrayList<>();
        Connection connection = Main.connect();
        String query = "SELECT * FROM tutor";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("tutorid");
            String username = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            int price = resultSet.getInt("price");
            String uni = resultSet.getString("university");
            tutors.add(new Tutor(id, username, password, email, price, uni));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return tutors;
    }

    public boolean changeTutorPassword(String oldPassword, String newPassword) throws SQLException {
        if (!this.password.equals(oldPassword)) {
            welcomePage.showInvalidOldPasswordError();
            return false;
        }
        if (!isValidPassword(newPassword)) {
            welcomePage.showInvalidNewPasswordError();
            return false;
        }
        Connection connection = Main.connect();
        String query = "UPDATE tutor SET password = ? WHERE tutorid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newPassword);
        statement.setInt(2, this.id);
        int rowsUpdated = statement.executeUpdate();
        statement.close();
        connection.close();

        if (rowsUpdated > 0) {
            this.password = newPassword; // Update the current password in the object
            return true;
        } 
        else {
            return false;
        }
    }

    public boolean changeTutorUsername(String newUsername) throws SQLException {
        if (!isValidUsername(newUsername)) {
            welcomePage.showInvalidUsernameError();
            return false;
        }
        if (!isUsernameUnique(newUsername, "tutor", "name")) {
            welcomePage.showNotUniqueUsernameError();
            return false;
        }
    
        Connection connection = Main.connect();
        String query = "UPDATE tutor SET name = ? WHERE tutorid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newUsername);
        statement.setInt(2, this.id);
    
        int rowsUpdated = statement.executeUpdate();
        statement.close();
        connection.close();
    
        if (rowsUpdated > 0) {
            this.username = newUsername; // Update the current username in the object
            return true;
        } else {
            return false;
        }
    }
}