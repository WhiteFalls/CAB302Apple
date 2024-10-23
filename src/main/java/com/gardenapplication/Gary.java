package com.gardenapplication;

import com.gardenapplication.controller.NavbarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Database.DatabaseInitializer.checkAndCreateDatabase;

public class Gary extends Application {
    /**
     * Starts the application
     * @param stage The stage that the application will run on
     * @throws IOException Thrown if problem arises with setting the stage
     */
    @Override
    public void start(Stage stage) throws IOException {

        checkAndCreateDatabase();
        FXMLLoader loader = new FXMLLoader(Gary.class.getResource("plant-info-page.fxml"));
        Scene scene = new Scene(loader.load(), 1400, 700);

        FXMLLoader navbarLoader = new FXMLLoader(Gary.class.getResource("navbar.fxml"));
        Node navbar = navbarLoader.load();  // Ensure the FXML is fully loaded
        NavbarController navbarController = navbarLoader.getController();

        navbarController.setStage(stage);

        stage.setTitle("Garden Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}