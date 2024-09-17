package com.example.gardenplanner.controller;

import Database.IPersonDAO;
import Database.PersonDAO;
import People.Person;
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
    public TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private IPersonDAO personDAO;

    public RegisterController() {
        personDAO = new PersonDAO();
    }

    @FXML
    public void registerUser() {
        // Validate user input before proceeding
        if (!validateInput()) {
            return;  // If validation fails, do not proceed
        }

        // Create a new Person object with the user input
        Person newPerson = new Person(
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText()
        );

        System.out.println("First Name: " + newPerson.getFirstName());
        System.out.println("Last Name: " + newPerson.getLastName());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + newPerson.getPassword());


        // Save the new user to the database
        personDAO.addPerson(newPerson);
        showAlert("Success", "User successfully registered!");

        // Clear input fields after successful registration
//        clearFields();
    }

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

        if (!password.equals(confirmPassword)) {
            showAlert("Validation Error", "Passwords do not match.");
            return false;
        }

        return true;  // If all validations pass, return true
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    public void initialize() {
        System.out.println("RegisterController initialized.");
    }
}
