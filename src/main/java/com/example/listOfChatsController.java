package com.example;
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
}
