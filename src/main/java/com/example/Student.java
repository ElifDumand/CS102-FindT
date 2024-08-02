package com.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Student extends User {

    public Student(int id, String name, String password, String email) {
        super(id, name, password, email);
    }

    @Override
    public String getAccountType() {
        return "Student";
    }

    public List<Timeslot> getTimeslots() throws SQLException {
        return Timeslot.getTimeslotsByStudent(this.getId());
    }
    
    public static Student getById(int studentid){
        try (Connection connection = Main.connect()) {
            String query = "SELECT * FROM student WHERE studentid = ?";
            try (PreparedStatement stat = connection.prepareStatement(query)) {
                stat.setInt(1, studentid);
                ResultSet r = stat.executeQuery();
                if (r.next()) {
                    String name = r.getString("name");
                    String password = r.getString("password");
                    String email = r.getString("email");

                    stat.close();
                    connection.close();
                    return new Student(studentid, name, password, email);
                }
                stat.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Student signUp(String username, String password, String email) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Connection connection = Main.connect();
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

    public static List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = Main.connect();
        String query = "SELECT * FROM tutor";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("studentid");
            String username = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            students.add(new Student(id, username, password, email));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return students;
    }

    public static Student logIn(String name, String password) throws SQLException {

		ResultSet r = getByUsername(name);
		if (r.next() && r.getString("password").equals(password)) {

			int id = r.getInt("studentid");

			String email = r.getString("email");
            Student newStudent = new Student(id, name, password, email);
            User.setCurrentUser(newStudent);
			return newStudent;

		}   

		r.close();
		welcomePage.showInvalidLoginError();
		return null;

	}

    public static ResultSet getByUsername(String name) throws SQLException {

		Connection connection = Main.connect();
		String query = "select * from student where name = ?";
		PreparedStatement stat = connection.prepareStatement(query);
		stat.setString(1, name);
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

    public boolean changeStudentPassword(String oldPassword, String newPassword) throws SQLException {
        if (!this.password.equals(oldPassword)) {
            welcomePage.showInvalidOldPasswordError();
            return false;
        }
        if (!isValidPassword(newPassword)) {
            welcomePage.showInvalidNewPasswordError();
            return false;
        }
        Connection connection = Main.connect();
        String query = "UPDATE student SET password = ? WHERE studentid = ?";
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

    public boolean changeStudentUsername(String newUsername) throws SQLException {
        if (!isValidUsername(newUsername)) {
            welcomePage.showInvalidUsernameError();
            return false;
        }
        if (!isUsernameUnique(newUsername, "student", "name")) {
            welcomePage.showNotUniqueUsernameError();
            return false;
        }
    
        Connection connection = Main.connect();
        String query = "UPDATE student SET name = ? WHERE studentid = ?";
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

    

    public static Student addStudent(int studentid, String name, String password, String email) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = Main.connect();
            
            String query = "INSERT INTO student (studentid, name, password, email) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, studentid);
            statement.setString(2, name);
            statement.setString(3, password);
            statement.setString(4, email);
            
            statement.executeUpdate();
            
            // Assuming Student class constructor matches the parameters
            return new Student(studentid, name, password, email);
            
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
            throw e; // Re-throw to propagate the exception to the caller
            
        } finally {
            // Close resources in finally block to ensure they are always closed
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}    