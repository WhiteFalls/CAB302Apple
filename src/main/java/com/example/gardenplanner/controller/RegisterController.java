package com.example.gardenplanner.controller;

import Database.IPersonDAO;
import Database.PersonDAO;
import People.Person;
import Util.EmailValidator;
import com.example.gardenplanner.HelloApplication;
import Util.ConfigKeyLoader;
import Util.BouncyCastleAESUtil;
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
import javax.crypto.SecretKey;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import java.util.Base64;

import java.io.IOException;

import java.sql.Connection;

public class RegisterController {

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
    private Label strengthLabel;
    @FXML
    private ProgressBar strengthBar;

    private Connection connection;

    private IPersonDAO personDAO;

    private SecretKey aesKey;

    public RegisterController() {
        personDAO = new PersonDAO();

        // Encryption good
        try {
            aesKey = ConfigKeyLoader.getSecretKeyFromConfig();  // Retrieve AES key from configuration
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            int strength = calculatePasswordStrength(newValue);
            updatePasswordStrengthUI(strength);
        });
    }

    /**
     * Register's the user into the database
     */
    @FXML
    public void registerUser() throws Exception {
        // Validate user input before proceeding
        if (!validateInput()) {
            return;
        }

        String password = passwordField.getText();
        byte[] iv = BouncyCastleAESUtil.generateIv();  // Generate a new IV for each password
        String encryptedPassword = BouncyCastleAESUtil.encrypt(password, aesKey, iv);
        String ivBase64 = Base64.getEncoder().encodeToString(iv);

        // Create a new Person object with the user input
        Person newPerson = new Person(
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                encryptedPassword,
                ivBase64
        );

        // Debugging
        System.out.println("First Name: " + newPerson.getFirstName());
        System.out.println("Last Name: " + newPerson.getLastName());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + encryptedPassword);
        System.out.println("Base64: " + ivBase64);



        // Save the new user to the database
        personDAO.addPerson(newPerson);
        showAlert("Success", "User successfully registered!");
    }

    /**
     * Validates whether the user's input was in the correct format
     * @return True if the user's inputs are valid, else false
     */
    private boolean validateInput() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

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
            showAlert("Validation Error", "Passwords do not match.");
            return false;
        }

        return true;  // If all validations pass, return true
    }

    /**
     * Displays an alert pop-up message
     * @param title The title of the alert message
     * @param message The message contained in the pop-up
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears all the fields on the page
     */
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

//    @FXML
//    public void initialize() {
//        System.out.println("RegisterController initialized.");
//    }

    /**
     * Sets scene to the login page of the application
     * @param event The event that triggers the page change
     */
    @FXML
    public void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Parent loginPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginPage, 1200, 600);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load login page.");
        }
    }

    private int calculatePasswordStrength(String password) {
        int strength = 0;

        // Check the length of the password
        if (password.length() >= 6) {
            strength++;
        }

        // Check if the password contains both letters and numbers
        if (password.matches(".*[A-Za-z].*") && password.matches(".*[0-9].*")) {
            strength++;
        }

        // Check for special characters
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            strength++;
        }

        // Check for mixed case letters
        if (password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")) {
            strength++;
        }

        return strength;  // Max strength value is 4
    }

    private void updatePasswordStrengthUI(int strength) {
        switch (strength) {
            case 0:
                strengthLabel.setText("Password Strength: Very Weak");
                strengthBar.setProgress(0.25);
                strengthBar.setStyle("-fx-accent: red;");
                break;
            case 1:
                strengthLabel.setText("Password Strength: Weak");
                strengthBar.setProgress(0.5);
                strengthBar.setStyle("-fx-accent: orange;");
                break;
            case 2:
                strengthLabel.setText("Password Strength: Medium");
                strengthBar.setProgress(0.75);
                strengthBar.setStyle("-fx-accent: yellow;");
                break;
            case 3:
            case 4:
                strengthLabel.setText("Password Strength: Strong");
                strengthBar.setProgress(1.0);
                strengthBar.setStyle("-fx-accent: green;");
                break;
        }
    }
}
