import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:GardenPlanner.db";

    public static void checkAndCreateDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            // Create tables if they do not exist
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
