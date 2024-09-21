package com.example.gardenplanner.controller;

import Database.GardenDAO;
import People.Garden;
import com.example.gardenplanner.HelloApplication;
import com.example.gardenplanner.UserSession;
import com.example.gardenplanner.controller.GardenManagementController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MainPage {
    @FXML
    private Button UpdatesButton;

    private UserSession currentUser = UserSession.getInstance();  // can be used to refactor initialize
    private GardenDAO gardenDAO;
    /**
     * Initialises the main page by getting the current user session
     */
    public void initialize() {
        // Get the current user session
        UserSession session = UserSession.getInstance();
        String firstName = session.getFirstName();
        String lastName = session.getLastName();
        String email = session.getEmail();

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
        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garden-management-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setScene(scene);
    }


    @FXML
    protected void addGarden() throws IOException{
        // this code wil be needed in the future to swap to the actual garden plot
//        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garden-plot.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
//        stage.setScene(scene);

        // for now, we just create a new garden in the database
        Garden garden = new Garden(currentUser.getPersonId(),currentUser.getFirstName()); // currently, garden name will be users name
        gardenDAO.addGarden(garden);
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

