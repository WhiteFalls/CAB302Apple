package com.example.gardenplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD

        checkAndCreateDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Garden Manager");
=======
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("usertodoList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        scene.getStylesheets().add(getClass().getResource("Css/stylestodoList.css").toExternalForm());
        stage.setTitle("To Do List");
>>>>>>> usertodoList
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}