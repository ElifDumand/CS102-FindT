package com.example;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public void initialize()
    {
        backButton.setOnAction(event -> {
            try 
            {
                handleListOfChatBack(event);
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
    private void handleListOfChatBack(ActionEvent event) throws IOException, SQLException
    {
        User currentUser = User.getCurrentUser();
        if(currentUser.getAccountType().equalsIgnoreCase("tutor")){
            App.setRoot("TutorMenu");
            }
        else{
            App.setRoot("studentMenu");
        }
    }
    
}
