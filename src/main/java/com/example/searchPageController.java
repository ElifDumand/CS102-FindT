package com.example;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> schoolComboBox;

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

        ArrayList<String> schools = new ArrayList<String>();
        schools.add("Bilkent University");
        schools.add("ODTÜ");
        schools.add("Uludağ University");
        schools.add("Boğaziçi University");
        schools.add("Gazi University");
        schools.add("Atatürk University");

        ObservableList<String> observableSchools = FXCollections.observableArrayList(schools);
        schoolComboBox.setItems(observableSchools);

        lessonComboBox.getItems().addAll(
            "Face-to-face",
            "online"
        );

        priceComboBox.getItems().addAll(
            300,
            350,
            400,
            450,
            500,
            550,
            600,
            650,
            700,
            750
        );


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
        if(currentUser.getAccountType().equalsIgnoreCase("tutor")){
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
    
    public ArrayList<Tutor> seearchTutorByName(String searchTerm) throws SQLException {
		ArrayList<Tutor> resultTutors = new ArrayList<Tutor>();
		Connection connection = Main.connect();
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			String query = "select * from tutor where name like ?";
			stat = connection.prepareStatement(query);
			stat.setString(1, "%" + searchTerm + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("tutorid");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				Tutor tutor = new Tutor(id, name, password, email);
				resultTutors.add(tutor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs != null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultTutors;
	}

    public ArrayList<Tutor> searchTutorBySubject(String searchTerm) throws SQLException {
        ArrayList<Tutor> resultTutors = new ArrayList<Tutor>();
        Connection connection = Main.connect();
        PreparedStatement stat = null;
        ResultSet rs = null;
    
        try {
            String query = "SELECT t.tutorid, t.name, t.password, t.email " +
                           "FROM tutor t " +
                           "JOIN subject s ON t.subjectid = s.subjectid " +
                           "WHERE s.subjectname LIKE ?";
            stat = connection.prepareStatement(query);
            stat.setString(1, "%" + searchTerm + "%");
    
            rs = stat.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("tutorid");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                Tutor tutor = new Tutor(id, name, password, email);
                resultTutors.add(tutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultTutors;
    }

    public ArrayList<Tutor> seearchTutorByUniversity(String searchTerm) throws SQLException {
		ArrayList<Tutor> resultTutors = new ArrayList<Tutor>();
		Connection connection = Main.connect();
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			String query = "select * from tutor where university like ?";
			stat = connection.prepareStatement(query);
			stat.setString(1, "%" + searchTerm + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("tutorid");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				Tutor tutor = new Tutor(id, name, password, email);
				resultTutors.add(tutor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs != null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultTutors;
	}

    public ArrayList<Tutor> seearchTutorByPrice(String searchTerm) throws SQLException {
		ArrayList<Tutor> resultTutors = new ArrayList<Tutor>();
		Connection connection = Main.connect();
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			String query = "select * from tutor where price like ?";
			stat = connection.prepareStatement(query);
			stat.setString(1, "%" + searchTerm + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("tutorid");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				Tutor tutor = new Tutor(id, name, password, email);
				resultTutors.add(tutor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs != null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultTutors;
	}


    
}
