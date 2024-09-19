package com.example.gardenplanner.controller;

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

    public void initialize() {
        // Get the current user session
        UserSession session = UserSession.getInstance();
        String firstName = session.getFirstName();
        String lastName = session.getLastName();
        String email = session.getEmail();
    }

    @FXML
    protected void onUpdatesButtonClick() throws IOException {
        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("usertodoList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setScene(scene);
    }

    @FXML
    protected void onGardenButtonClick() throws IOException {
        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garden-management-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setScene(scene);
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

