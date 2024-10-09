package com.example.gardenplanner.controller;

import Database.PersonDAO;
import People.Person;
import Util.BouncyCastleAESUtil;
import Util.ConfigKeyLoader;
import Util.EmailValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.crypto.SecretKey;
import java.util.Base64;

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
    private SecretKey aesKey;

    public SettingsController() {
        PersonDAO = new PersonDAO();

        // Encryption good
        try {
            aesKey = ConfigKeyLoader.getSecretKeyFromConfig();  // Retrieve AES key from configuration
        } catch (Exception e) {
            e.printStackTrace(); // if it fails
        }
    }

    @FXML
    public void updatefname(ActionEvent event) {
        String fname = firstNameField.getText();

    }
}


    @FXML
    public void loginUser(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            // Retrieve the person(s) from the database (I'm working on it)
            Person person = new PersonDAO().getPersonByEmail(email);

            if (person != null && validateInput(email, password)) {
                String encryptedPassword = person.getPassword();
                String ivBase64 = person.getIvBase64();

                // Decode the IV from Base64
                byte[] iv = Base64.getDecoder().decode(ivBase64);

                // Decrypt the stored password
                String decryptedPassword = BouncyCastleAESUtil.decrypt(encryptedPassword, aesKey, iv);
            }
        }
        catch (Exception e) {
        e.printStackTrace();
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

