package com.example.gardenplanner.controller;

import com.example.gardenplanner.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private static final String DB_URL = "jdbc:sqlite:GardenPlanner.sqlite";  // Updated database location

    @FXML
    public void loginUser(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (validateInput(email, password)) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    showAlert("Login Successful!", "Welcome back, " + rs.getString("fname"));
                    clearFields();
                    goToMainPage(event);
                } else {
                    showAlert("Login Failed", "Incorrect Email or Password.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to login user.");
            }
        }
    }

    // Validate User input
    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Email and password are required.");
            return false;
        }

        return true;
    }

    // alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear after login
    private void clearFields() {
        emailField.clear();
        passwordField.clear();
    }


    // Handle registration link click to go to the registration page
    @FXML
    public void goToMainPage(ActionEvent event) {
        try {
            // Load the registration page
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("main-page.fxml"));
            Parent registrationPage = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene to the current stage
            Scene scene = new Scene(registrationPage, 1200, 600);
            stage.setScene(scene);
            stage.setTitle("Main Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load main page.");
        }
    }

    // Handle registration link click to go to the registration page
    @FXML
    public void goToRegistration(ActionEvent event) {
        try {
            // Load the registration page
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Parent registrationPage = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene to the current stage
            Scene scene = new Scene(registrationPage, 1200, 600);
            stage.setScene(scene);
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load registration page.");
        }
    }
}
