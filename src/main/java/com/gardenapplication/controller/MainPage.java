package com.gardenapplication.controller;

import Database.*;
import GardenCell.Garden;
import People.IPerson;
import Util.Popup;
import com.gardenapplication.Gary;
import com.gardenapplication.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;


public class MainPage {
    @FXML
    public Text welcomeText;
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

        gardenDAO = new GardenDAO();
        gardenMapDAO = new GardenMapDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);
        int gardensOwned = findNumGardensOwned();
        if (gardensOwned == 1){
            setButtonToRemove();
        }

        welcomeText.setText("Welcome Back, " + currentUser.getFirstName());
    }

    /**
     * Method for users to add a garden
     * @throws SQLException
     */
    @FXML
    protected void addGarden() throws SQLException {

            int gardensOwned = findNumGardensOwned();
            if (gardensOwned >= 1){
                Popup.displayErrorPopup("You are only allowed to own one garden!");
                // change button to remove
                setButtonToRemove();
            }
            else if (gardensOwned == 0){
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Create a Garden");
                dialog.setHeaderText("Set Garden Name");
                dialog.setContentText("Please enter your garden name:");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent() && !result.get().isEmpty() && result.get().trim().length() > 1) {
                    Garden garden = new Garden(result.get(),currentUser.getPersonId(),2,2);
                    System.out.println("garden name: " + garden.getGardenName());
                    gardenDAO.addGarden(garden);
                    gardenUsersDAO.addPersonToGarden(loggedInUser, garden, "Manager"); // adds user to garden users
                    gardenMapDAO.createDefaultMap(garden);
                    setButtonToRemove();
                }
                else{
                    Popup.displayErrorPopup("Garden name must include a character!");
                }
            }
            else{
                System.out.println("Error: Unable to retrieve the number of gardens owned.");
                displayUnknownError();
            }
    }

    /**
     * Changes the button text to be "Add Garden" and set its action to add a garden.
     */
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

    /**
     * Changes button text to be "Remove Garden" and sets its action to be remove garden
     */
    private void setButtonToRemove() {
        addGarden.setText("Remove Garden");
        addGarden.setOnAction(e -> handleRemoveGardenButton(gardenDAO.getGardenByUserId(loggedInUser.getUserId())));
    }

    /**
     * Controls the text and on-action of the button based on the number of owned gardens by the user
     * @param garden The garden to be added/removed
     */
    private void handleRemoveGardenButton(Garden garden) {
    if (Popup.displayConfirmPopup("Are you sure you want to delete your garden: " +garden.getGardenName() + "?")){
        gardenDAO.deleteGarden(garden.getGardenId());
    }
        int numGardens = findNumGardensOwned(); // check if garden is removed from database
        System.out.println(numGardens) ;
        if (numGardens == 0) // only allowed to own one garden
        {
            Popup.displayErrorPopup("Garden successfully removed!");
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
            Popup.displayErrorPopup("Garden has not been removed, please try again!");
        }
        else{
            displayUnknownError();
        }

    }

    /**
     * Finds the number of gardens the user owns
     * @return The amount of gardens owned by the user, otherwise -1.
     */
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

    /**
     * Display error popup
     */
    private void displayUnknownError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("AN ERROR OCCURRED IN THE APPLICATION");
        alert.showAndWait();
    }

}

