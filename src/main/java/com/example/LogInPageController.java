package com.example;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LogInPageController {

    @FXML
    private Button LogInButtonStudent;

    @FXML
    private Button LogInButtonTutor;

    @FXML
    private TextField studentPassword;

    @FXML
    private Text studentSignInText;

    @FXML
    private TextField studentUsername;

    @FXML
    private TextField tutorPassword;

    @FXML
    private Text tutorSignInText;

    @FXML
    private TextField tutorUsername;

    public LogInPageController()
    {

    }

    @FXML
    public void initialize()
    {
        LogInButtonStudent.setOnAction(event -> {
            try {
                handleLogInButtonStudent(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        LogInButtonTutor.setOnAction(event -> {
            try {
                handleLogInButtonTutor(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        studentSignInText.setOnMouseClicked(event -> {
            try {
                handleStudentSignInText(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        tutorSignInText.setOnMouseClicked(event -> {
            try {
                handleTutorSignInText(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

    }

    @FXML
    private void handleLogInButtonStudent(ActionEvent event) throws IOException
    {
        App.setRoot("studentTemp");
    }

    @FXML
    private void handleLogInButtonTutor(ActionEvent event) throws IOException
    {
        App.setRoot("Tutormenu");
    }

    @FXML
    private void handleStudentSignInText(MouseEvent event) throws IOException
    {
        App.setRoot("SignIn(Student)");
    }

    @FXML
    private void handleTutorSignInText(MouseEvent event) throws IOException
    {
        App.setRoot("SignIn(Tutor)");
    }





}
