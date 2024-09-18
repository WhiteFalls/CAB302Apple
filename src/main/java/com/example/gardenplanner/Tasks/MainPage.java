package com.example.gardenplanner.Tasks;

import com.example.gardenplanner.GardenManagementController;
import com.example.gardenplanner.HelloApplication;
import com.example.gardenplanner.RegisterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javax.imageio.spi.RegisterableService;
import java.awt.*;
import java.io.IOException;


public class MainPage {
    @FXML
    Button UpdatesButton;

    @FXML
    protected void onUpdatesButtonClick() throws IOException {
        Stage stage = (Stage) UpdatesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(GardenManagementController.class.getResource("garden-management.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), GardenManagementController.WIDTH, GardenManagementController.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    protected void OnNextButtonClick("Garden") throws IOException {
        Stage stage = (Stage) nextbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(. class.getResource("usertodoList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),.WIDTH, .HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    protected void OnNextButtonClick("Settings") throws IOException {
        Stage stage = (Stage) onNextbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(. class.getResource("settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),.WIDTH, .HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    protected void OnNextButtonClick("Settings") throws IOException {
        Stage stage = (Stage) onNextbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(. class.getResource(".fxml"));
        Scene scene = new Scene(fxmlLoader.load(),.WIDTH, .HEIGHT);
        stage.setScene(scene);
    }
}

