package com.example.gardenplanner.controller;

import Database.IPersonDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private final IPersonDAO personDAO;

    // Database URL for SQLite
    private static final String DB_URL = "jdbc:sqlite:GardenPlanner.db";

    public RegisterController() {
        personDAO = new PersonDAO();  // Connect to the actual database
    }

    @FXML
    public void registerUser(String name, String email) {
        // Create a new Person object with the user input
        Person newPerson = new Person(name, email);

        // Check if the person already exists
        if (personExists(newPerson)) {
            // Handle the case where the user is already registered
            showAlert("Error", "User already exists!");
        } else {
            // Save the new user to the database
            personDAO.addPerson(newPerson);
            showAlert("Success", "User successfully registered!");
        }
    }

    // Checks to see if user exists
    private boolean personExists(Person person) {
        Person existingPerson = personDAO.getPersonByEmail(person.getEmail());
        return existingPerson != null;
    }

    // Validates user input
    private boolean validateInput(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Validation Error", "Passwords do not match.");
            return false;
        }

        return true;
    }

    // Show an alert dialog with a message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clears the fields after successful registrationn
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}
