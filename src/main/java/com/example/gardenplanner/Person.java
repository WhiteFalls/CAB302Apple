package com.example.gardenplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person implements IPerson {
    private String firstName;
    private String lastName;
    private final String userId;
    private String password;
    private String email;
    /**
     * Returns person's name
     * return a string of the user's name
     */

    // Constructor to initialize the person object from the database :P
    public Person(String userId) {
        this.userId = userId;
        loadPersonFromDatabase();
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updatePersonInDatabase("fname", firstName);
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
        updatePersonInDatabase("lname", lastName);
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        throw new UnsupportedOperationException("User ID is immutable.");
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        updatePersonInDatabase("password", password);
    }

    @Override
    public String getEmail() {
        return email;

    }

    @Override
    public void setEmail(String email) {
        this.email = email;
        updatePersonInDatabase("email", email);
    }

    // Fetches details from THE database
    private void loadPersonFromDatabase() {
        String dbUrl = "jdbc:sqlite:GardenPlanner.db";

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String query = "SELECT fname, lname, email, password FROM Users WHERE user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.firstName = resultSet.getString("fname");
                this.lastName = resultSet.getString("lname");
                this.email = resultSet.getString("email");
                this.password = resultSet.getString("password");
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Updates a single field in the database for the current user
    private void updatePersonInDatabase(String field, String value) {
        String dbUrl = "jdbc:sqlite:GardenPlanner.db";

        String updateQuery = "UPDATE Users SET " + field + " = ? WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User " + field + " updated successfully.");
            } else {
                System.out.println("Failed to update " + field + " for user " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}