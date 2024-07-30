package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class StudentMenuPage {

    @FXML
    private ListView<String> tutorListView;

    @FXML
    private Button viewTutorsButton;

    @FXML
    private Button bookTutorButton;

    public void initialize() {
        // Set action handlers for the buttons
        viewTutorsButton.setOnAction(event -> displayTutors());
        bookTutorButton.setOnAction(event -> bookSelectedTutor());

        // Fetch and display tutors when the page opens
        displayTutors();
    }

    private Connection connect() {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/your_database_name"; // Update with your database name
        String user = "your_username"; // Update with your database username
        String password = "your_password"; // Update with your database password

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tutor> fetchTutorsFromDatabase() {
        List<Tutor> tutors = new ArrayList<>();
        String query = "SELECT tutorid, name, password, email, price, university FROM tutor"; // Ensure columns are correct

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                try {
                    Tutor tutor = new Tutor(
                            rs.getInt("tutorid"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getInt("price"),
                            rs.getString("university")
                    );
                    tutors.add(tutor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tutors;
    }

    private void displayTutors() {
        List<Tutor> tutors = fetchTutorsFromDatabase();
        Set<Tutor> chosenTutors = new HashSet<>();
        
        // Loop 65 times to choose random tutors without duplication
        for (int i = 0; i < 65; i++) {
            List<Tutor> availableTutors = new ArrayList<>(tutors);
            availableTutors.removeAll(chosenTutors);

            if (!availableTutors.isEmpty()) {
                Collections.shuffle(availableTutors); // Shuffle to get random order
                chosenTutors.add(availableTutors.get(0)); // Add the first tutor from the shuffled list
            }

            if (chosenTutors.size() >= 5) {
                break; // Stop if we have selected 5 tutors
            }
        }

        tutorListView.getItems().clear(); // Clear the list before adding new items
        for (Tutor tutor : chosenTutors) {
            tutorListView.getItems().add(tutor.getUsername()); // Display the username of the tutor
        }
    }

    private void bookSelectedTutor() {
        String selectedTutor = tutorListView.getSelectionModel().getSelectedItem();
        if (selectedTutor != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Booking Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Successfully booked tutor: " + selectedTutor);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No tutor selected. Please select a tutor to book.");
            alert.showAndWait();
        }
    }
}
