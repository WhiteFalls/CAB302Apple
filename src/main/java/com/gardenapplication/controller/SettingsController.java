package com.gardenapplication.controller;
import Database.IPersonDAO;
import Database.PersonDAO;
import People.IPerson;
import People.Person;
import Util.EmailValidator;
import Util.ConfigKeyLoader;
import Util.BouncyCastleAESUtil;
import com.gardenapplication.UserSession;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javax.crypto.SecretKey;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import java.io.IOException;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public class SettingsController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    public TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;

    public int userID;

    private Tooltip passwordTooltip;

    private Connection connection;

    private IPersonDAO personDAO;

    private SecretKey aesKey;

    public SettingsController() {
        personDAO = new PersonDAO();

        // Encryption good
        try {
            aesKey = ConfigKeyLoader.getSecretKeyFromConfig();  // Retrieve AES key from configuration
        } catch (Exception e) {
            e.printStackTrace(); // if it fails
        }
    }

    // Updates specific details of the person in the database
    @SuppressWarnings("SqlResolve")
    public void updatePersonInDatabase(String field, String value, Connection connection) {
        // Define a whitelist of valid fields
        Set<String> allowedFields = new HashSet<>();
        allowedFields.add("fname");
        allowedFields.add("lname");
        allowedFields.add("email");
        allowedFields.add("password");
        allowedFields.add("iv_base64");

        // Check if the provided field is allowed
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Invalid field: " + field);
        }

        // Safe query with the valid field name
        String updateQuery = "UPDATE Users SET " + field + " = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userID);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User " + field + " updated successfully.");
            } else {
                System.out.println("Failed to update " + field + " for user " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        // Get the current user session
        UserSession session = UserSession.getInstance();
        String firstName = session.getFirstName();
        String lastName = session.getLastName();
        String email = session.getEmail();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInput() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        System.out.println(password);
        System.out.println(confirmPassword);

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return false;
        }

        // Check if the email is valid
        if (!EmailValidator.isValidEmail(email)) {
            showAlert("Validation Error", "Invalid Email Format");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Validation Error", "Passwords do not match.");
            return false;
        }

        if (personDAO.isEmailRegistered(email)) {
            showAlert("Validation Error", "User Is already registered.");
            return false;
        }

        return true;  // If all validations pass, return true
    }

}


