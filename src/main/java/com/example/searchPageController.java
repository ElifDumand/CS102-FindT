package com.example;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class searchPageController {
    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> lessonComboBox;

    @FXML
    private ComboBox<Integer> priceComboBox;

    @FXML
    private TextField searchSubjectBar;

    @FXML
    private ScrollPane subjectsListedScrollPane;
    
    @FXML
    private VBox subjectsListedVBox;

    private Stage stage;
    private Scene scene; 

    @FXML
    public void initialize()
    {
        backButton.setOnAction(event -> {
            try 
            {
                handleSearchPageBack(event);
            } 
            catch (IOException e ) {
                e.printStackTrace(); 
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        });

    }


    @FXML
    private void handleSearchPageBack(ActionEvent event) throws IOException, SQLException
    {
        User currentUser = User.getCurrentUser();
        if(currentUser.getAccountType().equals("Teacher")){
            App.setRoot("TutorMenu");
            }
        else{
            App.setRoot("studentMenu");
        }
    }

    //public void goToMenuPage(MouseEvent event){
    //    User currentUser = User.getCurrentUser();
    ///    Parent root;
    //    if(currentUser.getAccountType().equals("Teacher")){
    //        root = FXMLLoader.load(getClass().getResource("TutorMenu.fxml"));
    //    }
    //  else{
    //        root = FXMLLoader.load(getClass().getResource("studentMenu.java"));
    //    }
    //    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    //    scene = new Scene(root);
    //    stage.setScene(scene);
    //    stage.show();
    //}
    
}
