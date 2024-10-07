package com.example.gardenplanner.controller;

import Database.*;
import People.Garden;
import People.IPerson;
import com.example.gardenplanner.HelloApplication;
import com.example.gardenplanner.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;


public class MainPage {
    @FXML
    private Button UpdatesButton;

    @FXML
    private Button addGarden;

    private UserSession currentUser = UserSession.getInstance();  // can be used to refactor initialize
    private GardenDAO gardenDAO;
    private Connection connection;
    private IPerson loggedInUser;
    private PersonDAO personDAO;
    private IGardenUsersDAO gardenUsersDAO;
    private GardenMapDAO gardenMapDAO;
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
        personDAO = new PersonDAO();
        int personId = UserSession.getInstance().getPersonId();
        this.loggedInUser = personDAO.getPerson(personId);

        gardenDAO = new GardenDAO(); // gardens can be created on main page
        gardenMapDAO = new GardenMapDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);
        int gardensOwned = findNumGardensOwned();
        if (gardensOwned == 1){
            setButtonToRemove();
        }
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
    protected void addGarden() throws SQLException {
        // this code wil be needed in the future to swap to the actual garden plot
//        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garden-plot.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
//        stage.setScene(scene);

            int gardensOwned = findNumGardensOwned();
            if (gardensOwned >= 1){
                displayErrorPopup("You are only allowed to own one garden!");
                // change button to remove
                setButtonToRemove();
            }
            else if (gardensOwned == 0){
                // for now, we just create a new garden in the database

                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Create a Garden");
                dialog.setHeaderText("Set Garden Name");
                dialog.setContentText("Please enter your garden name:");

                Optional<String> result = dialog.showAndWait();
                // the result.get.trim is needed as putting a name with a space actually destroys the database :(
                if (result.isPresent() && !result.get().isEmpty() && result.get().trim().length() > 1) { // holy salad
                    Garden garden = new Garden(result.get(),currentUser.getPersonId(),2,2);
                    System.out.println("garden name: " + garden.getGardenName());
                    gardenDAO.addGarden(garden);
                    gardenUsersDAO.addPersonToGarden(loggedInUser, garden, "Manager"); // adds user to garden users (whoever presses add garden is
                    gardenMapDAO.createDefaultMap(garden);
                    setButtonToRemove();
                }
                else{
                    displayErrorPopup("Garden name must include a character!");
                }
            }
            else{
                System.out.println("Error: Unable to retrieve the number of gardens owned.");
                displayUnknownError();
            }
    }

    private void setButtonToAdd() {
        addGarden.setText("Add Garden");
        addGarden.setOnAction(e -> {
            try {
                addGarden();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void setButtonToRemove() {
        addGarden.setText("Remove Garden");
        addGarden.setOnAction(e -> handleRemoveGardenButton(gardenDAO.getGardenByUserId(loggedInUser.getUserId())));
    }

    private void handleRemoveGardenButton(Garden garden) {
    if (displayConfirmPopup("Are you sure you want to delete your garden: " +garden.getGardenName() + "?")){
        gardenDAO.deleteGarden(garden.getGardenId());
    }
        int numGardens = findNumGardensOwned(); // check if garden is removed from database
        System.out.println(numGardens) ;
        if (numGardens == 0) // only allowed to own one garden
        {
            displayPopup("Garden successfully removed!");
            addGarden.setText("Add Garden");
            addGarden.setOnAction(e -> {
                try {
                    setButtonToAdd();
                    addGarden();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

        }
        else if (numGardens == 1){
            displayErrorPopup("Garden has not been removed, please try again!");
        }
        else{
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
        return -1; // handle this case
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

    // stolen from gardenmanagement controller - we really need a utlis class
    private void displayPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // stolen from gardenmanagement controller - we really need a utlis class
    private boolean displayConfirmPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            return true;
        }
        return false;
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

