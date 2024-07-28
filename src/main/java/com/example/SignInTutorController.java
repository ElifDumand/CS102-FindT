package com.example;
import java.io.IOException;

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
    private Circle TutorCreateProfileCR;

    @FXML
    private Rectangle TutorCreateProfileRect;

    @FXML
    private ComboBox<?> TutorExperience;

    @FXML
    private TextField TutorMail;

    @FXML
    private TextField TutorPassword;

    @FXML
    private TextField TutorUsernameSignIn;

    @FXML
    private Circle tutorCreateProfileCL;
    
    private Stage stage;
    private Scene scene;

    @FXML
    private void handleTutorSignIn(MouseEvent event) throws IOException
    {
        App.setRoot("SignIn(Tutor)");
    }
}
