package com.example;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignInStudentController {
    @FXML
    private Button BackToLogInStudent;

    @FXML
    private ChoiceBox<Integer> ParentsAgeChoiceBox;

    @FXML
    private ChoiceBox<Integer> StudentBirthyearChoiceBox;

    @FXML
    private TextField StudentMail;

    @FXML
    private TextField StudentPasswordSignIn;

    @FXML
    private TextField StudentUsernameSignIn;

    @FXML
    private Button createProfileButton;
    

    @FXML
    public void initialize()
    {
        //ParentsAgeChoiceBox = new ChoiceBox<>();
        //StudentBirthyearChoiceBox = new ChoiceBox<>();

        //This is for the parents age, I think 79 is a good upper limit
        Integer[] parentAge = new Integer[60];
        for(int i = 0; i < parentAge.length; i++)
        {
            parentAge[i] = i + 20;
        }
        ParentsAgeChoiceBox.getItems().addAll(parentAge);

        Integer[] studentAge = new Integer[43];
        for(int i = 0; i < studentAge.length; i++)
        {
            studentAge[i] = i + 7;
        }
        StudentBirthyearChoiceBox.getItems().addAll(studentAge);

        StudentBirthyearChoiceBox.setValue(14);
        ParentsAgeChoiceBox.setValue(35);

        BackToLogInStudent.setOnAction(event -> {try {
            handleBackButton(event);
        } catch (IOException e) {
            e.printStackTrace();
        }});

        createProfileButton.setOnAction(event -> {try {
            handleCreateStudentProfile(event);
        } 
        catch (IOException e ) {
            e.printStackTrace();
        }
        catch (SQLException e ) {
            e.printStackTrace();
        }});



    }
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException
    {
        App.setRoot("LogInPage");
    }

    @FXML
    private void handleCreateStudentProfile(ActionEvent event) throws IOException, SQLException
    {
        String username = StudentUsernameSignIn.getText();
        String password = StudentPasswordSignIn.getText();
        String email = StudentMail.getText();

        if (username != null && password != null && email != null) {
            try 
            {
                Student newStudent = Student.signUp(username, password, email);
                User.setCurrentUser(newStudent);
                App.setRoot("LogInPage");
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
    } 
    }


    @FXML
    private void handleStudentSignIn(MouseEvent event) throws IOException
    {
        App.setRoot("SignInStudentController");
    }

}
