package com.gardenapplication.controller;

import Util.Popup;
import com.gardenapplication.Gary;
import com.gardenapplication.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javax.crypto.SecretKey;
import java.util.Base64;
import Util.ConfigKeyLoader;
import Util.BouncyCastleAESUtil;
import Database.PersonDAO;
import People.Person;
import java.io.IOException;


public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private SecretKey aesKey;

    private static final String DB_URL = "jdbc:sqlite:GardenPlanner.sqlite";  // Updated database location

    public LoginController() {
        try {
            aesKey = ConfigKeyLoader.getSecretKeyFromConfig();  // Very awesome.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to grab the user's typed details and log them in
     * @param event The even that triggers the method
     */
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

                // Compare the decrypted password
                if (decryptedPassword.equals(password)) {
                    UserSession session = UserSession.getInstance();
                    session.setPersonId(person.getUserId());
                    session.setEmail(email);
                    session.setFirstName(person.getFirstName());
                    session.setLastName(person.getLastName());

                    Popup.showAlert("Login Successful!", "Welcome back, " + person.getFirstName() + " " + person.getLastName());
                    clearFields();
                    goToMainPage(event);
                }
            }
            else {
                Popup.showAlert("Login Failed", "Incorrect Email or Password.");
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates whether the user's input is a valid format
     * @param email The user's email
     * @param password The user's password
     * @return True if the format is valid, else false
     */
    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Popup.showAlert("Validation Error", "Email and password are required.");
            return false;
        }

        return true;
    }

    /**
     * Clears the login fields
     */
    private void clearFields() {
        emailField.clear();
        passwordField.clear();
    }


    /**
     * Sets scene back to the main page of the application
     * @param event The event that triggers the page change
     */
    @FXML
    public void goToMainPage(ActionEvent event) {
        try {
            // Load the registration page
            FXMLLoader loader = new FXMLLoader(Gary.class.getResource("main-page.fxml"));
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
            Popup.showAlert("Error", "Unable to load main page.");
        }
    }

    /**
     * Sets scene to the registration page of the application
     * @param event The event that triggers the page change
     */    @FXML
    public void goToRegistration(ActionEvent event) {
        try {
            // Load the registration page
            FXMLLoader loader = new FXMLLoader(Gary.class.getResource("register.fxml"));
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
            Popup.showAlert("Error", "Unable to load registration page.");
        }
    }
}
