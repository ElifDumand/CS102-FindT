package com.example;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignInTutorController {
    @FXML
    private Button BackToLogInTutor;

    @FXML
    private ComboBox<String> TutorBranch;

    @FXML
    private ComboBox<String> TutorExperience;

    @FXML
    private TextField TutorMail;

    @FXML
    private TextField TutorPassword;

    @FXML
    private TextField TutorUsernameSignIn;

    @FXML
    private Button createProfileButton;

    @FXML
    private ComboBox<Integer> price;

    @FXML
    private Text priceText;

    
    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize()
    {
        TutorBranch.getItems().addAll("Maths",
         "Physics", 
         "Chemistry",
         "Biology");

         
         TutorExperience.getItems().addAll("1", 
        "2",
        "3",
        "4",
        "5", 
        "5+");

        price.getItems().addAll(
        200,
        250, 
        300,
        350,
        400,
        450,
        500,
        550,
        600,
        650,
        700,
        750);

        price.setValue(500);
        TutorExperience.setValue("3");
        TutorBranch.setValue("Maths");


        BackToLogInTutor.setOnAction(event -> {
            try 
            {
            handleBackButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        
        createProfileButton.setOnAction(event -> {
            try 
            {
            handleCreateTutorProfile(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        });
        
    }

    @FXML
    private void handleCreateTutorProfile(ActionEvent event) throws IOException, SQLException
    {
        String username = TutorUsernameSignIn.getText();
        String password = TutorPassword.getText();
        String email = TutorMail.getText();

        if (username != null && password != null && email != null) {
            try 
            {
                Tutor newTutor = Tutor.signUp(username, password, email);
                User.setCurrentUser(newTutor);
                App.setRoot("LogInPage");
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
    } 
    }
    

    private void handleBackButton(ActionEvent event) throws IOException
    {
        App.setRoot("LogInPage");
    }

    @FXML
    private void handleTutorSignIn(MouseEvent event) throws IOException
    {
        App.setRoot("SignInTutorController");
    }
}
