package com.example;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class StudentMenuController implements Initializable{

    @FXML
    private Button chatStudent;

    @FXML
    private Text courseName1;

    @FXML
    private Text courseName2;

    @FXML
    private Text lessonType1;

    @FXML
    private Text lessonType2;

    @FXML
    private Text mailText;

    @FXML
    private Button myScheduleButton;

    @FXML
    private Text price1;

    @FXML
    private Text price2;

    @FXML
    private Circle profileStudent;

    @FXML
    private Circle profileStudent1;

    @FXML
    private Circle profileStudent2;

    @FXML
    private Circle profileStudent3;

    @FXML
    private Circle profileStudent4;

    @FXML
    private Circle profileStudent5;

    @FXML
    private Circle profileStudent6;

    @FXML
    private TextField searchBar;

    @FXML
    private Text sendMessage1;

    @FXML
    private Text sendMessage2;

    @FXML
    private Text sendMessage3;

    @FXML
    private Text sendMessage4;

    @FXML
    private Text sendMessage5;

    @FXML
    private Text sendMessage6;

    @FXML
    private Button settingsStudent;

    @FXML
    private Text syllabus1;

    @FXML
    private Text syllabus2;

    @FXML
    private Text syllabus3;

    @FXML
    private Text syllabus4;

    @FXML
    private Text syllabus5;

    @FXML
    private Text syllabus6;

    @FXML
    private Text tutorName1;

    @FXML
    private Text tutorName2;

    @FXML
    private Text usernameText;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        chatStudent.setOnAction(event -> {
            try {
                handleChatStudentButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        settingsStudent.setOnAction(event -> {
            try {
                handleSettingsStudentButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        searchBar.setOnMouseClicked(event -> {
            try {
                handleSearchBar(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        myScheduleButton.setOnAction(event -> {
            try {
                handleMyScheduleButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        User currentUser = User.getCurrentUser();
        mailText.setText(currentUser.getEmail());
        usernameText.setText(currentUser.getUsername());
    }

    @FXML
    private void handleChatStudentButton(ActionEvent event) throws IOException
    {
        App.setRoot("listOfChats");
    }

    @FXML
    private void handleMyScheduleButton(ActionEvent event) throws IOException
    {
        App.setRoot("StudentSchedulePage");
    }

    @FXML
    private void handleSettingsStudentButton(ActionEvent event) throws IOException
    {
        App.setRoot("settings");
    }


    @FXML
    private void handleSearchBar(MouseEvent event) throws IOException
    {
        App.setRoot("searchPage");
    }
    
    public void recommendTutor(){
        List<Tutor> tutors = fetchTutorsFromDatabase();
        
    }



    private  List<Tutor> fetchTutorsFromDatabase() {
        List<Tutor> tutors = new ArrayList<>();
        String query = "SELECT tutorid, name, password, email FROM tutor"; // Ensure columns are correct

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                try {
                    Tutor tutor = new Tutor(
                            rs.getInt("tutorid"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                    tutors.add(tutor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tutors;
    }

    private Connection connect() {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/your_database_name"; // Update with your database name
        String user = "your_username"; // Update with your database username
        String password = "your_password"; // Update with your database password

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


