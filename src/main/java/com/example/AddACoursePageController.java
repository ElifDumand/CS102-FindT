package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class AddACoursePageController {

    @FXML
    private Button addCourseButton;

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<String> branchNameChoiceBox;

    @FXML
    private ChoiceBox<String> lessonTypeChoiceBox;

    @FXML
    private ChoiceBox<Integer> priceChoiceBox;


    @FXML
public void initialize()
{
    backButton.setOnAction(event -> {try {
        handlebackButton(event);
    } catch (IOException e) {
        e.printStackTrace();
    }});

    String[] lessonType = {"Online", "Face to face"};
    lessonTypeChoiceBox.getItems().addAll(lessonType);

    String[] courseList = {"Maths", "Physics", "Chemistry", "Biology"};
    branchNameChoiceBox.getItems().addAll(courseList);

    Integer[] prices = {200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750};
    priceChoiceBox.getItems().addAll(prices);
}


    private void handlebackButton(ActionEvent event) throws IOException
    {
        App.setRoot("TutorMenu");
    }

}



    

