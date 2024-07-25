package com.example;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignInStudentController implements Initializable{
    @FXML
    private Button BackToLogInStudent;

    @FXML
    private ChoiceBox<Integer> ParentsAgeChoiceBox;

    @FXML
    private ChoiceBox<Integer> StudentBirthyearChoiceBox;

    @FXML
    private Text StudentCreateProfile;

    @FXML
    private Circle StudentCreateProfileCL;

    @FXML
    private Circle StudentCreateProfileCR;

    @FXML
    private Rectangle StudentCreateProfileRect;

    @FXML
    private TextField StudentMail;

    @FXML
    private TextField StudentPasswordSignIn;

    @FXML
    private TextField StudentUsernameSignIn;

    @FXML
    private Button createProfileButton;
    
    private Stage stage;
    private Scene scene;



    @FXML
    public void initialize()
    {
        //This is for the parents age, I think 79 is a good upper limit
        Integer[] parentAge = new Integer[60];
        for(int i = 0; i < parentAge.length; i++)
        {
            parentAge[i] = i + 20;
        }
        ParentsAgeChoiceBox.getItems().addAll(parentAge);

        Integer[] studentBirthYear = new Integer[40];
        for(int i = 0; i < studentBirthYear.length; i++)
        {
            studentBirthYear[i] = i + 1980;
        }
        StudentBirthyearChoiceBox.getItems().addAll(studentBirthYear);

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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

}
