package com.example;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class chatPageController implements Initializable{
    
    @FXML
    private Button backButton;

    @FXML
    private FlowPane chatFlowPane;

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

    
    


    public void renderMessage(Message message) throws SQLException {
        String name = "default";
        User user = User.getCurrentUser();
        if (message.getSenderType().equalsIgnoreCase("student")) {
            name = Student.getById(message.getSenderId()).getUsername();
        } else {
            name = Tutor.getById(message.getSenderId()).getUsername();
        }

        int currentUserId = user.getId();
        if (message.isSentByCurrentUser(currentUserId)) {
            Pane messagePane = createMessagePane(name, message.getBody(), "right");
            chatFlowPane.getChildren().add(messagePane);
        } else {
            Pane messagePane = createMessagePane(name, message.getBody(), "left");
            chatFlowPane.getChildren().add(messagePane);
        }
    }


    public void postMessage(String receiverType, int receiverId) throws SQLException {
        String text = messageTextField.getText();
        User currentUser = User.getCurrentUser();
        String senderType = currentUser.getAccountType();
        int senderId = currentUser.getId();

        Message message = Message.createMessage(senderType, senderId, receiverType, receiverId, text);
        renderMessage(message);
        messageTextField.setText("");
    }

    public void renderMessages(String receiverType, int receiverId) throws SQLException {
        ArrayList<Message> messages = Message.getMessages(receiverType, receiverId);
        for (Message message : messages) {
            renderMessage(message);
        }
    }

    private void handleBackButton(ActionEvent event) throws IOException
    {
        try
        {
            App.setRoot("listOfChats");
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public void renderMessages() throws SQLException {

        ArrayList<Message> messages = Message.getMessages(User.getCurrentUser().getAccountType(), User.getCurrentReceiver().getId());
        for (Message message : messages) {
            renderMessage(message);
        }
    }

    public void displayMessage(Message message) throws SQLException {
        String name = User.getById(message.getSenderId()).getUsername();
        if (message.isSentByCurrentUser(User.getCurrentUser().getId())) {
            Pane messagePane = createMessagePane(name, message.getBody(), "right");
            chatFlowPane.getChildren().add(messagePane);
        } else {
            Pane messagePane = createMessagePane(name, message.getBody(), "left");
            chatFlowPane.getChildren().add(messagePane);
        }
    }

    private Pane createMessagePane(String userName, String text, String alignment) throws SQLException {
        // Create an HBox to hold the text
        HBox messageBox = new HBox();
        HBox nameBox = new HBox();
        nameBox.setSpacing(5);
        messageBox.setSpacing(5);
        messageBox.setAlignment(Pos.CENTER);

        // Create the text node
        Text messageText = new Text(text);
        messageText.setFont(Font.font("Times New Roman", 14));
        if (messageText.getText().length() > 100) {
            messageText.setWrappingWidth(200);
        }
        

        Text name = new Text("@" + userName);
        name.setFont(Font.font("Times New Roman", 12));
        messageBox.setAlignment(Pos.CENTER); 
        messageBox.getChildren().add(messageText);
        FlowPane messagePane = new FlowPane();
        FlowPane messageP = new FlowPane();
        messagePane.setPrefWidth(575);
        messageP.setPrefHeight(0);
        messagePane.setPrefHeight(0);
        name.setTextAlignment(TextAlignment.JUSTIFY);

        if ("right".equalsIgnoreCase(alignment)) {
            messageText.setFill(Color.WHITE);
            name.setFill(Color.BLACK);
            messageBox.setStyle("-fx-background-color:  #d4b9e1; -fx-padding: 5px; -fx-background-radius: 5px;");
            messageP.getChildren().add(messageBox);
            messageP.getChildren().add(name);
            messageP.setOrientation(Orientation.VERTICAL);
            messagePane.getChildren().add(messageBox);
            // messagePane.getChildren().add(nameBox);
            messagePane.getChildren().add(messageP);
            messageP.setAlignment(Pos.CENTER_RIGHT);
            messagePane.setAlignment(Pos.CENTER_RIGHT);

            FlowPane.setMargin(messageBox, new javafx.geometry.Insets(0, 0, 0, 20)); //
        } else {
            messageText.setFill(Color.BLACK);
            name.setFill(Color.BLACK);
            messageBox.setStyle("-fx-background-color: #314975; -fx-padding: 5px; -fx-background-radius: 5px;");
            messageP.getChildren().add(messageBox);
            messageP.getChildren().add(name);
            messageP.setOrientation(Orientation.VERTICAL);
            messagePane.getChildren().add(messageBox);
            messagePane.getChildren().add(messageP);
            messagePane.setAlignment(Pos.CENTER_LEFT);
            messageP.setAlignment(Pos.CENTER_LEFT);
            FlowPane.setMargin(messageBox, new javafx.geometry.Insets(0, 20, 0, 0));
        }
        

        return messagePane;
    }

    public void sentMessageAction(ActionEvent event) throws SQLException{
        
        Message m = Message.createMessage(User.getCurrentUser().getAccountType(), User.getCurrentUser().getId(), User.getCurrentReceiver().getAccountType(), User.getCurrentReceiver().getId(), messageTextField.getText());
        messageTextField.setText("");
        renderMessage(m);
    }

    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(event -> {
            try 
            {
                handleBackButton(event);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        });
        sendMessageButton.setOnAction(event -> {
            try {
                sentMessageAction(event);
            } catch (SQLException e) {
                System.out.println("a");
            }
        });
    }

}