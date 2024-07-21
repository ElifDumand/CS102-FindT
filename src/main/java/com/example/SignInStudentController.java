package com.example;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignInStudentController {
    @FXML
    private Button BackToLogInStudent;

    @FXML
    private ChoiceBox<Integer> ParentsAgeChoiceBox;

    @FXML
    private ChoiceBox<Integer> StudentBirthyearChoiceBox;

    @FXML
    private Text StudentCreateProfile;

    @FXML
    private Circle StudentCreateProfileCL;

    @FXML
    private Circle StudentCreateProfileCR;

    @FXML
    private Rectangle StudentCreateProfileRect;

    @FXML
    private TextField StudentMail;

    @FXML
    private TextField StudentPasswordSıgnIn;

    @FXML
    private TextField StudentUsernameSıgnIn;
    
    private Stage stage;
    private Scene scene;

    public void goStudentMenuPage(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("studentMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize()
    {
        //This is for the parents age, I think 79 is a good upper limit
        Integer[] parentAge = new Integer[60];
        for(int i = 0; i < parentAge.length; i++)
        {
            parentAge[i] = i + 20;
        }
        ParentsAgeChoiceBox.getItems().addAll(parentAge);

        Integer[] studentBirthYear = new Integer[40];
        for(int i = 0; i < studentBirthYear.length; i++)
        {
            studentBirthYear[i] = i + 1980;
        }
        StudentBirthyearChoiceBox.getItems().addAll(studentBirthYear);

    }


}
