package com.example;
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

    public void goToMenuPage(MouseEvent event) throws Exception{
        User currentUser = User.getCurrentUser();
        Parent root;
        if(currentUser.getAccountType().equals("Teacher")){
            root = FXMLLoader.load(getClass().getResource("TutorMenu.fxml"));
        }
        else{
            root = FXMLLoader.load(getClass().getResource("studentMenu.java"));
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void logOut(MouseEvent event){
        User currentUser = User.getCurrentUser();
        Parent root = FXMLLoader.load(getClass().getResource("LogInPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        User.logOut();
    }
}

