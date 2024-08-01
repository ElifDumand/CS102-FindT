package com.example;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class listOfChatsController implements Initializable{
    @FXML
    private Button backButton;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox chatVBox;

    private Stage stage;
    private Scene scene;

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

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
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

            displayListOfChats();
    
         
        }
        }

        private void displayListOfChats()
        {
            if(User.getCurrentUser().getAccountType().equalsIgnoreCase("Student"))
            {
                List<Tutor> allTutors = new ArrayList<>();
                try {
                    allTutors = Tutor.getAllTutors();
                } 
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
        
                    for (Tutor tutor : allTutors) {
                        BorderPane chatBox = new BorderPane();
                        chatBox.setStyle("-fx-background-color: #493175; -fx-padding: 5px; -fx-background-radius: 5px;");
                        chatBox.setPrefWidth(400);
                        Text tutorInfo = new Text(tutor.getUsername());
                        tutorInfo.setFont(new Font("Times New Roman", 16));
                        tutorInfo.setFill(Color.WHITE);
                        tutorInfo.setTextAlignment(TextAlignment.CENTER);

                        Button chatButton = new Button("Send message");
                        chatButton.setFont(Font.font("Times New Roman", 12));

                        chatButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    App.setRoot("chatPage");
                                    User.setCurrentReceiver(tutor);
                                } catch (IOException ex) {
                                }
                            }
                        });

                        chatBox.setLeft(tutorInfo);
                        chatBox.setRight(chatButton);
                        chatVBox.getChildren().add(chatBox);
                        chatVBox.setPrefHeight(chatVBox.getPrefHeight() + chatBox.getHeight());
                        chatVBox.setVisible(true);
                    }
            }
            else if(User.getCurrentUser().equals("tutor"))
            {
                List<Student> students = new ArrayList<>(); 
                try {
                    students = Message.getChatsForTutor(User.getCurrentUser().getId());
                } catch (SQLException ex) {
                    System.out.println("a");
                }
                    for (Student student : students) {
                        BorderPane chatBox = new BorderPane();
                        chatBox.setStyle("-fx-background-color: #493175; -fx-padding: 5px; -fx-background-radius: 5px;");
                        chatBox.setPrefWidth(400);
                        Text studentInfo = new Text(student.getUsername());
                        studentInfo.setFont(new Font("Times New Roman", 16));
                        studentInfo.setFill(Color.WHITE);
                        studentInfo.setTextAlignment(TextAlignment.CENTER);

                        Button chatButton = new Button("Send message");
                        chatButton.setFont(Font.font("Times New Roman", 12));

                        chatButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try 
                                {
                                    App.setRoot("settings");
                                    User.setCurrentReceiver(student);
                                } 
                                catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                        chatBox.setLeft(studentInfo);
                        chatBox.setRight(chatButton);
                        chatVBox.getChildren().add(chatBox);
                        chatVBox.setPrefHeight(chatVBox.getPrefHeight() + chatBox.getHeight());
                        chatVBox.setVisible(true);
                        chatBox.setVisible(true);
                    }


            }



        }
}
