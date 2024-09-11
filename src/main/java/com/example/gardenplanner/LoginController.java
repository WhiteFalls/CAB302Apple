package com.example.gardenplanner;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {


    public class Login {

        @FXML
        private TextField emailField;
        @FXML
        private PasswordField passwordField;

        private static final String DB_URL = "jdbc:sqlite:gardenplanner.db";

        @FXML
        private void loginUser() {
            String email = emailField.getText();
            String password = passwordField.getText();

            if(validateInput(email, password)) {
                try (Connection conn = DriverManager.getConnection(DB_URL)){
                    String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, email);
                    pstmt.setString(2, password);

                    ResultSet rs = pstmt.executeQuery();

                    if(rs.next()) {
                        showAlert("Login Successful!", "Welcome back,");
                        clearFields();
                    } else {
                        showAlert("Login Failed,", "Incorrect Email or Password.");
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
        }
    }
