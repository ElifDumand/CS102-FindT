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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class searchPageController {
    @FXML
    private Button backButton;

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
        schools.add("METU");
        schools.add("Uludağ University");
        schools.add("Boğaziçi University");
        schools.add("Gazi University");
        schools.add("Atatürk University");

        ObservableList<String> observableSchools = FXCollections.observableArrayList(schools);
        schoolComboBox.setItems(observableSchools);


        priceComboBox.getItems().addAll(
			10,
			20, 
			30,
			40,
			50,
			60,
			70);
        


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

        priceComboBox.setValue(50);
        schoolComboBox.setValue("METU");
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

    public ArrayList<Tutor> searchTutorByName(String searchTerm) throws SQLException {
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
				int price = rs.getInt("price");
				String uni = rs.getString("university");
				Tutor tutor = new Tutor(id, name, password, email, price, uni);
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
				int price = rs.getInt("price");
				String uni = rs.getString("university");
                Tutor tutor = new Tutor(id, name, password, email, price, uni);
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

    public ArrayList<Tutor> searchTutorByUniversity(String searchTerm) throws SQLException {
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
				int price = rs.getInt("price");
				String uni = rs.getString("university");
				Tutor tutor = new Tutor(id, name, password, email, price, uni);
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

    public ArrayList<Tutor> searchTutorByPrice(String searchTerm) throws SQLException {
		ArrayList<Tutor> resultTutors = new ArrayList<Tutor>();
		Connection connection = Main.connect();
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			String query = "select * from tutor where price <= ?";
			stat = connection.prepareStatement(query);
			stat.setString(1, "%" + searchTerm + "%");

			rs = stat.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("tutorid");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				int price = rs.getInt("price");
				String uni = rs.getString("university");

				Tutor tutor = new Tutor(id, name, password, email, price, uni);
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

    public void displayResults(MouseEvent event) throws SQLException {
		subjectsListedVBox.getChildren().clear();
		ArrayList<Tutor> targetPrice = searchTutorByPrice(priceComboBox.getValue() + "");
		ArrayList<Tutor> targetUniversity = searchTutorByUniversity(schoolComboBox.getValue());

				for (Tutor tutor : targetPrice) {
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
                            } catch (IOException ex) {
                            }
						}
					});

					chatBox.setLeft(tutorInfo);
					chatBox.setRight(chatButton);
					subjectsListedVBox.getChildren().add(chatBox);
					subjectsListedVBox.setPrefHeight(subjectsListedVBox.getPrefHeight() + chatBox.getHeight());
					subjectsListedVBox.setVisible(true);
				}

                for (Tutor tutor : targetUniversity) {
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
                            } catch (IOException ex) {
                            }
						}
					});

					chatBox.setLeft(tutorInfo);
					chatBox.setRight(chatButton);
					subjectsListedVBox.getChildren().add(chatBox);
					subjectsListedVBox.setPrefHeight(subjectsListedVBox.getPrefHeight() + chatBox.getHeight());
					subjectsListedVBox.setVisible(true);
				}
			
                
			} 
		}


