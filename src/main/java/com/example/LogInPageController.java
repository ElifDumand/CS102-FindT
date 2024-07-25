package com.example;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LogInPageController {

    @FXML
    private Button LogInButtonStudent;

    @FXML
    private Button LogInButtonTutor;

    @FXML
    private PasswordField studentPassword;

    @FXML
    private Text studentSignInText;

    @FXML
    private TextField studentUsername;

    @FXML
    private PasswordField tutorPassword;

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
            try 
            {
                handleLogInButtonStudent(event);
            } catch (IOException | SQLException e) {
                e.printStackTrace(); 
            }
        });

        LogInButtonTutor.setOnAction(event -> {
            try 
            {
                handleLogInButtonTutor(event);
            } catch (IOException | SQLException e) {
                e.printStackTrace(); 
            }
        });

        studentSignInText.setOnMouseClicked(event -> {
            try 
            {
                handleStudentSignInText(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        tutorSignInText.setOnMouseClicked(event -> {
            try 
            {
                handleTutorSignInText(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

    }

    @FXML
    private void handleLogInButtonStudent(ActionEvent event) throws IOException, SQLException
    {
        if(Student.logIn(studentUsername.getText(),studentPassword.getText()) != null){
            App.setRoot("studentMenu");
        }
    }

    @FXML
    private void handleLogInButtonTutor(ActionEvent event) throws IOException, SQLException
    {
        if(Tutor.logIn(tutorUsername.getText(),tutorPassword.getText()) != null){
            App.setRoot("TutorMenu");
        }
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
