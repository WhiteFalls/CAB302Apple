package com.example.gardenplanner;

import java.sql.*;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:GardenPlanner.db";

    public static void checkAndCreateDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String createTablesQuery = """
                PRAGMA foreign_keys = ON;

                CREATE TABLE IF NOT EXISTS Users (
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fname TEXT NOT NULL,
                    lname TEXT NOT NULL,
                    email TEXT NOT NULL,
                    password TEXT NOT NULL
                );
            
                CREATE TABLE IF NOT EXISTS Gardens (
                    garden_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    garden_owner INTEGER NOT NULL,
                    garden_name TEXT NOT NULL,
                    FOREIGN KEY (garden_owner) REFERENCES Users(user_id)
                );

                CREATE TABLE IF NOT EXISTS Garden_Users (
                    garden_id INTEGER NOT NULL,
                    user_id INTEGER NOT NULL,
                    access_level TEXT NOT NULL,
                    PRIMARY KEY (garden_id, user_id),
                    FOREIGN KEY (garden_id) REFERENCES Gardens(garden_id),
                    FOREIGN KEY (user_id) REFERENCES Users(user_id)
                );

                CREATE TABLE IF NOT EXISTS Tasks (
                    task_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    garden_id INTEGER NOT NULL,
                    assigned_date TEXT NOT NULL,
                    due_date TEXT NOT NULL,
                    task_details TEXT,
                    category TEXT NOT NULL,  -- Add category as a TEXT field but is an enum
                    FOREIGN KEY (user_id) REFERENCES Users(user_id),
                    FOREIGN KEY (garden_id) REFERENCES Gardens(garden_id)
                );

                CREATE TABLE IF NOT EXISTS Garden_Map (
                    garden_id INTEGER NOT NULL,
                    x INTEGER NOT NULL,
                    y INTEGER NOT NULL,
                    plant_name TEXT NOT NULL,
                    PRIMARY KEY (garden_id, x, y),
                    FOREIGN KEY (garden_id) REFERENCES Gardens(garden_id)
                );

                CREATE TABLE IF NOT EXISTS Plant_Information (
                    plant_name TEXT PRIMARY KEY,
                    plant_information TEXT
                );
            """;
            statement.executeUpdate(createTablesQuery);
            System.out.println("Database and tables created successfully.");

            checkAndInsertDefaultUsersAndGarden(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkAndInsertDefaultUsersAndGarden(Connection connection) {
        try {
            // Check if the users already exist
            String userCheckQuery = "SELECT COUNT(*) AS userCount FROM Users WHERE fname IN ('test', 'Liam', 'John')";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(userCheckQuery);

            if (rs.next() && rs.getInt("userCount") == 0) {
                // If no users exist, insert test, Liam, and John
                String insertUsersQuery = """
                    INSERT INTO Users (fname, lname, email, password) VALUES
                    ('test', 'user', 'test@test.com', 'testpass'),
                    ('Liam', 'Smith', 'liam@test.com', 'liampass'),
                    ('John', 'Doe', 'john@test.com', 'johnpass');
                """;
                stmt.executeUpdate(insertUsersQuery);

                // Fetch user IDs for Liam and John
                int liamId = getUserIdByName(connection, "Liam");
                int johnId = getUserIdByName(connection, "John");

                // Insert a garden for Liam (assuming garden_id is 1)
                String insertGardenQuery = "INSERT INTO Gardens (garden_owner, garden_name) VALUES (?, ?)";
                PreparedStatement gardenStmt = connection.prepareStatement(insertGardenQuery);
                gardenStmt.setInt(1, liamId);
                gardenStmt.setString(2, "Liam's Garden");
                gardenStmt.executeUpdate();

                System.out.println("Garden for Liam created successfully.");

                // Insert tasks for Liam and John, associate with garden 1
                String insertTasksQuery = """
                    INSERT INTO Tasks (user_id, garden_id, assigned_date, due_date, task_details) VALUES
                    (?, 1, '2024-09-01', '2024-09-15', 'Water the plants'),
                    (?, 1, '2024-09-01', '2024-09-10', 'Prune the shrubs');
                """;
                PreparedStatement taskStmt = connection.prepareStatement(insertTasksQuery);
                taskStmt.setInt(1, liamId);
                taskStmt.setInt(2, johnId);

                taskStmt.executeUpdate();
                System.out.println("Default users, garden, and tasks inserted.");
            } else {
                System.out.println("Users already exist, skipping insertion.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getUserIdByName(Connection connection, String firstName) throws SQLException {
        String query = "SELECT user_id FROM Users WHERE fname = ?";
        try (PreparedStatement prep = connection.prepareStatement(query)) {
            prep.setString(1, firstName);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        }
        throw new SQLException("User with first name " + firstName + " not found.");
    }
}

//Debug SQL
//ALTER TABLE Tasks ADD COLUMN category TEXT DEFAULT 'DAILY';