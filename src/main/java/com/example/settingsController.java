package com.example;
import java.io.IOException;
import java.sql.SQLException;

import org.w3c.dom.events.MouseEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class settingsController {

    @FXML
    private Button logOutButton;

    @FXML
    private Button settingsBackButton;

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

    public void initialize()
    {
            settingsBackButton.setOnAction(event -> {
            try 
            {
                handleSettingsBack(event);
            } 
            catch (IOException e ) {
                e.printStackTrace(); 
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        });

        logOutButton.setOnAction(event -> {
            try 
            {
                handleLogOut(event);
            } 
            catch (IOException e ) {
                e.printStackTrace(); 
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        });

    }

    @FXML
    private void handleSettingsBack(ActionEvent event) throws IOException, SQLException
    {
        User currentUser = User.getCurrentUser();
        if(currentUser.getAccountType().equalsIgnoreCase("tutor")){
            App.setRoot("TutorMenu");
            }
        else{
            App.setRoot("studentMenu");
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException, SQLException
    {
        User currentUser = null;
        App.setRoot("LogInPage");
    }



    @FXML
    private void logOut(MouseEvent event) throws IOException
    {
        App.setRoot("LogInPage");
    }
}

