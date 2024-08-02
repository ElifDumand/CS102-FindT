package com.example;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class StudentMenuController implements Initializable{


    @FXML
    private Button chatStudent;

    @FXML
    private Text courseName1;

    @FXML
    private Text courseName2;

    @FXML
    private Text courseName3;

    @FXML
    private Text courseName4;

    @FXML
    private Text courseName5;

    @FXML
    private Text courseName6;

    @FXML
    private Text mailText;

    @FXML
    private Button myScheduleButton;

    @FXML
    private Text price1;

    @FXML
    private Text price2;

    @FXML
    private Text price3;

    @FXML
    private Text price4;

    @FXML
    private Text price5;

    @FXML
    private Text price6;

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
    private Text tutorName3;

    @FXML
    private Text tutorName4;

    @FXML
    private Text tutorName5;

    @FXML
    private Text tutorName6;

    @FXML
    private Text usernameText;

    @FXML
    private Rectangle rectangle1;

    @FXML
    private Rectangle rectangle2;

    @FXML
    private Rectangle rectangle3;

    @FXML
    private Rectangle rectangle4;

    @FXML
    private Rectangle rectangle5;

    @FXML
    private Rectangle rectangle6;

   //List<Tutor> recommend = fetchTutorsFromDatabase();

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
        Text[] tutorNames = {tutorName1, tutorName2, tutorName3, tutorName4, tutorName5, tutorName6};
        Text[] courseNames = {courseName1, courseName2, courseName3, courseName4, courseName5, courseName6};
        Text[] price = {price1, price2, price3, price4, price5, price6};
        Text[] texts = {syllabus1, syllabus2, syllabus3, syllabus4, syllabus5, syllabus6};
        Rectangle[] rectangles = {rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6};
        Text[] messageTexts = {sendMessage1, sendMessage2, sendMessage3, sendMessage4, sendMessage5, sendMessage6};


        List<Tutor> recommend = fetchTutorsFromDatabase();
        int size = Math.min(6, recommend.size());

        for (int idx = 0; idx < size; idx++) {
            int finalidx = idx;
            tutorNames[idx].setText(recommend.get(idx).getUsername());
            courseNames[idx].setText(recommend.get(idx).getTutorSubject());
            price[idx].setText("$" + recommend.get(idx).getTutorPrice());
            messageTexts[idx].setOnMouseClicked(event -> {
                try {
                    User.setCurrentReceiver(recommend.get(finalidx));
                    handSendMessageRectangle(event);
                    
                } catch (IOException e) {
                    e.printStackTrace(); 
                }
            });
            rectangles[idx].setOnMouseClicked(event -> {
                try {
                    User.setCurrentReceiver(recommend.get(finalidx));
                    handSendMessageRectangle(event);
                    
                } catch (IOException e) {
                    e.printStackTrace(); 
                }
            });
        }
        
        setSyllabus(texts);

    }

    private void setSyllabus(Text[] syllabusTexts) {
        List<Tutor> recommend = fetchTutorsFromDatabase();
        Map<String, String> syllabusMap = new HashMap<>();
        syllabusMap.put("Maths", "Limits,\nDerivatives,\nIntegrals");
        syllabusMap.put("Physics", "Kinematics, \nDynamics, \nEnergy");
        syllabusMap.put("Chemistry", "Compounds, \nStoichiometry, \nBonds");
        syllabusMap.put("Biology", "Genetics, \nEvolution, \nEcology");

        for (int idx = 0; idx < syllabusTexts.length; idx++) {
            Tutor tutor = recommend.get(idx);
            String subject = tutor.getTutorSubject();
            if (syllabusMap.containsKey(subject)) 
            {
                syllabusTexts[idx].setText(syllabusMap.get(subject));
            } else 
            {
                syllabusTexts[idx].setText("No syllabus available");
            }
        }
    }


    @FXML
    private void handSendMessageRectangle(MouseEvent event) throws IOException
    {
        App.setRoot("chatPage");
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
        
        tutorName1.setText(tutors.get(0).getUsername());
    }


    private  List<Tutor> fetchTutorsFromDatabase() {
        List<Tutor> tutors = new ArrayList<>();
        String query = "SELECT tutorid, name, password, email, price, university, subjectname FROM tutor"; // Ensure columns are correct

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                try {
                    Tutor tutor = new Tutor(
                            rs.getInt("tutorid"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getInt("price"),
                            rs.getString("university"),
                            rs.getString("subjectname")
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
        String url = "jdbc:mysql://localhost:3306/findTdatabase"; 
        String user = "root"; 
        String password = "root1root";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    }



