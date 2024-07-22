package com.example;
import java.io.IOException;
import java.util.Stack;

import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.events.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class chatPageController{
    @FXML
    private Button backButton;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private Circle generalProfileSign;

    @FXML
    private TextField messageTextField;

    @FXML
    private Ellipse profielBigCircle;

    @FXML
    private Circle profileLittleCircle;

    @FXML
    private Button sendMessageButton;

    @FXML
    private Text tutorNameText;
    
    private Stage stage;
    private Scene scene;

    @FXML
    private void handleSearchPageBack(MouseEvent event) throws IOException
    {
            App.setRoot("listOfChats");
    }

}