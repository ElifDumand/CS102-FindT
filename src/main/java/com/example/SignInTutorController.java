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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignInTutorController {
    @FXML
    private Button BackToLogInTutor;

    @FXML
    private ComboBox<?> TutorBranch;

    @FXML
    private Text TutorCreateProfile;

    @FXML
    private ComboBox<?> TutorExperience;

    @FXML
    private TextField TutorMail;

    @FXML
    private TextField TutorPassword;

    @FXML
    private TextField TutorUsernameSignIn;

    @FXML
    private Button createProfileButton;

    
    private Stage stage;
    private Scene scene;

    @FXML
    private void handleTutorSignIn(MouseEvent event) throws IOException
    {
        App.setRoot("SignIn(Tutor)");
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
    
    @FXML
    private void handleTutorSignInBack(MouseEvent event) throws IOException
    {
        App.setRoot("LogInPage");
    }
}
