package com.example;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SignInTutorController {
    @FXML
    private Button BackToLogInTutor;

    @FXML
    private ComboBox<String> TutorBranch;

    @FXML
    private TextField TutorMail;

    @FXML
    private TextField TutorPassword;

    @FXML
    private TextField TutorUsernameSignIn;

    @FXML
    private Text UniversityText;

    @FXML
    private Button createProfileButton;

    @FXML
    private ComboBox<Integer> price;

    @FXML
    private Text priceText;

    @FXML
    private ComboBox<String> universityComboBox;

    @FXML
    public void initialize()
    {
        TutorBranch.getItems().addAll("Maths",
         "Physics", 
         "Chemistry",
         "Biology");

         
        universityComboBox.getItems().addAll("Bilkent University", 
        "METU",
        "Uludağ University",
        "Boğaziçi University",
        "Gazi University", 
        "Atatürk University");


        price.getItems().addAll(
        10,
        20, 
        30,
        40,
        50,
        60,
        70);

        price.setValue(50);
        universityComboBox.setValue("METU");
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
