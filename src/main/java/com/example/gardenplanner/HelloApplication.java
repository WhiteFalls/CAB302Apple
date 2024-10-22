package com.example.gardenplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.gardenplanner.DatabaseInitializer.checkAndCreateDatabase;

public class HelloApplication extends Application {
    /**
     * Starts the application
     * @param stage The stage that the application will run on
     * @throws IOException Thrown if problem arises with setting the stage
     */
    @Override
    public void start(Stage stage) throws IOException {

        checkAndCreateDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("plant-info-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Garden Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}