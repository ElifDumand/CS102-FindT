package com.example;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class StudentMenuController {

    @FXML
    private Button chatStudent;

    @FXML
    private Text mailText;

    @FXML
    private Text onlineText;

    @FXML
    private Text priceText;

    @FXML
    private Circle profileStudent;

    @FXML
    private TextField searchBar;

    @FXML
    private Button sendMessageButton;

    @FXML
    private Button sendMessageButton2;

    @FXML
    private Button settingsStudent;

    @FXML
    private Text tutorFieldText;

    @FXML
    private Circle tutorProfile;

    @FXML
    private Circle tutorProfile2;

    @FXML
    private Text tutornameText;

    @FXML
    private Text usernameText;

    @FXML
    private Button myScheduleButton;

    @FXML
    public void initialize()
    {
        chatStudent.setOnAction(event -> {
            try {
                handleChatStudentButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        settingsStudent.setOnAction(event -> {
            try {
                handleSettingsStudentButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        searchBar.setOnMouseClicked(event -> {
            try {
                handleSearchBar(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });

        myScheduleButton.setOnAction(event -> {
            try {
                handleMyScheduleButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });
    }

    @FXML
    private void handleChatStudentButton(ActionEvent event) throws IOException
    {
        App.setRoot("listOfChats");

    }

    @FXML
    private void handleMyScheduleButton(ActionEvent event) throws IOException
    {
        App.setRoot("settings");
    }

    @FXML
    private void handleSettingsStudentButton(ActionEvent event) throws IOException
    {
        App.setRoot("settings");

    }



    @FXML
    private void handleSearchBar(MouseEvent event) throws IOException
    {
        App.setRoot("searchPage");
    }



}


