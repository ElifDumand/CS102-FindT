package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.events.MouseEvent;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class chatPageController {
    @FXML
    private Button backButton;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private Circle generalProfileSign;

    @FXML
    private TextField messageTextField;

    @FXML
    private FlowPane chatFlowPane;

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
    private void handleSearchPageBack(MouseEvent event) throws IOException {
        App.setRoot("listOfChats");
    }

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

    private Pane createMessagePane(String userName, String text, String alignment) throws SQLException {
        HBox messageBox = new HBox();
        HBox nameBox = new HBox();
        nameBox.setSpacing(5);
        messageBox.setSpacing(5);
        messageBox.setAlignment(Pos.CENTER);

        Text messageText = new Text(text);
        messageText.setFont(Font.font("Times New Roman", 14));
        if (messageText.getText().length() > 100) {
            messageText.setWrappingWidth(200);
        }

        Text name = new Text("@" + userName);
        name.setFont(Font.font("Times New Roman", 12));
        messageBox.getChildren().add(messageText);
        FlowPane messagePane = new FlowPane();
        FlowPane messageP = new FlowPane();
        messagePane.setPrefWidth(400);
        messageP.setPrefHeight(0);
        messagePane.setPrefHeight(0);
        name.setTextAlignment(TextAlignment.JUSTIFY);

        if ("right".equals(alignment)) {
            messageText.setFill(Color.WHITE);
            name.setFill(Color.BLACK);
            messageBox.setStyle("-fx-background-color: #053c75; -fx-padding: 5px; -fx-background-radius: 5px;");
            messageP.getChildren().add(messageBox);
            messageP.getChildren().add(name);
            messageP.setOrientation(Orientation.VERTICAL);
            messagePane.getChildren().add(messageBox);
            messagePane.getChildren().add(messageP);
            messageP.setAlignment(Pos.CENTER_RIGHT);
            messagePane.setAlignment(Pos.CENTER_RIGHT);
        } else {
            messageText.setFill(Color.BLACK);
            name.setFill(Color.BLACK);
            messageBox.setStyle("-fx-background-color: #b4bfc9; -fx-padding: 5px; -fx-background-radius: 5px;");
            messageP.getChildren().add(messageBox);
            messageP.getChildren().add(name);
            messageP.setOrientation(Orientation.VERTICAL);
            messagePane.getChildren().add(messageBox);
            messagePane.getChildren().add(messageP);
            messagePane.setAlignment(Pos.CENTER_LEFT);
            messageP.setAlignment(Pos.CENTER_LEFT);
        }

        return messagePane;
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
}