package com.gardenapplication.controller;

import Database.IPersonDAO;
import Database.PersonDAO;
import People.Person;
import Util.Popup;
import Util.EmailValidator;
import com.gardenapplication.Gary;
import Util.ConfigKeyLoader;
import Util.BouncyCastleAESUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javax.crypto.SecretKey;
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
    private Label errorLabel;

    private Tooltip passwordTooltip;

    private IPersonDAO personDAO;

    private SecretKey aesKey;

    public RegisterController() {
        personDAO = new PersonDAO();

        try {
            aesKey = ConfigKeyLoader.getSecretKeyFromConfig();  // Retrieve AES key from configuration
        } catch (Exception e) {
            e.printStackTrace(); // if it fails
        }
    }

    @FXML
    public void initialize() {
        passwordTooltip = new Tooltip();
        passwordField.setTooltip(passwordTooltip);

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = validatePassword(newValue);
            updatePasswordFieldUI(isValid);
            updateTooltip(newValue);
        });
    }

    /**
     * Register's the user into the database
     */
    @FXML
    public void registerUser(ActionEvent event) throws Exception {
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
        Popup.showAlert("Success", "User successfully registered!");

        goToLogin(event);
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
        System.out.println(password);
        System.out.println(confirmPassword);

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Popup.showAlert("Validation Error", "All fields are required.");
            return false;
        }

        // Check if the email is valid
        if (!EmailValidator.isValidEmail(email)) {
            Popup.showAlert("Validation Error", "Invalid Email Format");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Popup.showAlert("Validation Error", "Passwords do not match.");
            return false;
        }

        if (personDAO.isEmailRegistered(email)) {
            Popup.showAlert("Validation Error", "User Is already registered.");
            return false;
        }

        return true;  // If all validations pass, return true
    }


    /**
     * Sets scene to the login page of the application
     * @param event The event that triggers the page change
     */
    @FXML
    public void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Gary.class.getResource("login.fxml"));
            Parent loginPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginPage, 1400, 700);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Popup.showAlert("Error", "Unable to load login page.");
        }
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) {
            errorLabel.setText("Password must be at least 8 characters long");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            errorLabel.setText("Password must contain at least one uppercase letter");
            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            errorLabel.setText("Password must contain at least one number");
            return false;
        }

        // Check for special characters
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            errorLabel.setText("Password must contain at least one special character");
            return false;
        }

        errorLabel.setText("");  // Clear the error message if the password is valid
        return true;
    }

    /**
     * Update the PasswordField UI when validation fails or succeeds
     * @param isValid Whether the password was valid or not
     */
    private void updatePasswordFieldUI(boolean isValid) {
        if (!isValid) {
            // Make the PasswordField border red when validation fails
            passwordField.setStyle("-fx-border-color: red;");
            errorLabel.setVisible(true);
        } else {
            // Reset the PasswordField border color when the password is valid
            passwordField.setStyle("");
            errorLabel.setVisible(false);
        }
    }

    /**
     * Update the tooltip with feedback based on the current password input
     * @param password The password to be validated
     */
    private void updateTooltip(String password) {
        if (password.length() < 8) {
            passwordTooltip.setText("Password must be at least 8 characters.");
        } else if (!password.matches(".*[A-Z].*")) {
            passwordTooltip.setText("Password must contain at least one uppercase letter.");
        } else if (!password.matches(".*[a-z].*")) {
            passwordTooltip.setText("Password must contain at least one lowercase letter.");
        } else if (!password.matches(".*[0-9].*")) {
            passwordTooltip.setText("Password must contain at least one number.");
        } else if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            passwordTooltip.setText("Password must contain at least one special character.");
        } else {
            passwordTooltip.setText("Password is strong.");
        }
    }
}
