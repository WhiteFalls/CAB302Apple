package com.gardenapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Gary.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Garden Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}