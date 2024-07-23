package com.example;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.events.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class listOfChatsController {
    @FXML
    private Button backButton;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox chatVBox;

    private Stage stage;
    private Scene scene;

    @FXML
    private void handleListOfChatBack(MouseEvent event) throws IOException
    {
        User currentUser = User.getCurrentUser();
        if(currentUser.getAccountType().equals("Teacher")){
            App.setRoot("TutorMenu");
            }
        else{
            App.setRoot("studentMenu");
        }
    }
}
