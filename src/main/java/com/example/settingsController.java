package com.example;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.events.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class settingsController {

    @FXML
    private TextField biographyTexTfield;

    @FXML
    private Button logOutButton;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private TextField newUsernameTextField;

    @FXML
    private PasswordField oldPasswordTextField;

    @FXML
    private Button passwordSubmitButton;

    @FXML
    private ScrollPane reservedClassScrollPane;

    @FXML
    private Button usernameSubmitButton;

    @FXML
    private Text usernameText;
    private Stage stage;
    private Scene scene;

    @FXML
    private void handleSettingsBack(MouseEvent event) throws IOException
    {
        User currentUser = User.getCurrentUser();
        if(currentUser.getAccountType().equals("Teacher")){
            App.setRoot("TutorMenu");
            }
        else{
            App.setRoot("studentMenu");
        }
    }

    @FXML
    private void logOut(MouseEvent event) throws IOException
    {
        App.setRoot("LogInPage");
    }
}

