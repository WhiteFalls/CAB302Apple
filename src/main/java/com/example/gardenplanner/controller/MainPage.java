package com.example.gardenplanner.controller;

import Database.DatabaseConnection;
import Database.GardenDAO;
import Database.PersonDAO;
import People.Garden;
import People.IPerson;
import com.example.gardenplanner.HelloApplication;
import com.example.gardenplanner.UserSession;
import com.example.gardenplanner.controller.GardenManagementController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;


public class MainPage {
    @FXML
    private Button UpdatesButton;

    private UserSession currentUser = UserSession.getInstance();  // can be used to refactor initialize
    private GardenDAO gardenDAO;
    private Connection connection;
    private IPerson loggedInUser;
    private PersonDAO personDAO;
    /**
     * Initialises the main page by getting the current user session
     */
    public void initialize() {
        // Get the current user session
        UserSession session = UserSession.getInstance();
        String firstName = session.getFirstName();
        String lastName = session.getLastName();
        String email = session.getEmail();

        connection = DatabaseConnection.getConnection();
        personDAO = new PersonDAO(connection);
        int personId = UserSession.getInstance().getPersonId();
        this.loggedInUser = personDAO.getPerson(personId);

        gardenDAO = new GardenDAO(); // gardens can be created on main page
    }

    /**
     * Sets scene to the user to-do-list page of the application
     */
    @FXML
    protected void onUpdatesButtonClick() throws IOException {
        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("usertodoList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setScene(scene);
    }

    /**
     * Sets scene to the garden management page of the application
     */
    @FXML
    protected void onGardenButtonClick() throws IOException {
        int gardensOwned = findNumGardensOwned();
        if (gardensOwned == 1) {
            Stage stage = (Stage) UpdatesButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garden-management-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
            stage.setScene(scene);
        } else if (gardensOwned == 0) {
            displayErrorPopup("You must create a garden first!");
        }
        else{
            displayUnknownError();
        }
    }


    @FXML
    protected void addGarden() throws IOException, SQLException {
        // this code wil be needed in the future to swap to the actual garden plot
//        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garden-plot.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
//        stage.setScene(scene);

            int gardensOwned = findNumGardensOwned();
            if (gardensOwned >= 1){
                displayErrorPopup("You are only allowed to own one garden!");
            }
            else if (gardensOwned == 0){
                // for now, we just create a new garden in the database
                Garden garden = new Garden(currentUser.getPersonId(),currentUser.getFirstName()); // currently, garden name will be users name
                gardenDAO.addGarden(garden);
            }
            else{
                System.out.println("Error: Unable to retrieve the number of gardens owned.");
                displayUnknownError();
            }
    }

    private int findNumGardensOwned(){
        String query = "SELECT COUNT(*) FROM Gardens WHERE garden_owner = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, loggedInUser.getUserId());
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1);
        }catch(SQLException e ) {
            e.printStackTrace();
        }
        return -1;
    }

    // could be public, made into an error class maybe
    private void displayUnknownError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("AN ERROR OCCURRED IN THE APPLICATION");
        alert.showAndWait();
    }

    private void displayErrorPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }







//
//    @FXML
//    protected void OnNextButtonClick("Settings") throws IOException {
//        Stage stage = (Stage) onNextbutton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(. class.getResource("settings.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(),.WIDTH, .HEIGHT);
//        stage.setScene(scene);
//    }
//
//    @FXML
//    protected void OnNextButtonClick("Settings") throws IOException {
//        Stage stage = (Stage) onNextbutton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(. class.getResource(".fxml"));
//        Scene scene = new Scene(fxmlLoader.load(),.WIDTH, .HEIGHT);
//        stage.setScene(scene);
//    }
}

