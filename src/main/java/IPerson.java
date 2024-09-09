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

    String getFirstName() {
        return firstName;
    }

    String getLastName(){
        return lastName;
    }

    String getName() {
        return firstName;
    }

    String getUserId() {
        return userId;
    }

    String getPassword() {
        return password;
    }
    String getEmail() {
        return email;
    }

    // Fetches details from the database
    private void loadPersonFromDatabase() {
        String dbUrl = "jdbc:sqlite:GardenPlanner.db";
        String dbUser = "root";
        String dbPassword = "password";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

            String query = "SELECT fname, lname, email, password FROM Users WHERE user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.firstName = resultSet.getString("fname");
                this.lastName = resultSet.getString("lname");
                this.email = resultSet.getString("email");
                this.password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}