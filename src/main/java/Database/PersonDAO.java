package Database;

import People.Person;

import java.sql.*;
public class PersonDAO implements IPersonDAO {

    private final Connection connection;

    // Constructor to initialize the database connection

    /**
     * Constructs a new GardenDAO and initializes the connection to the database
     */
    public PersonDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void addPerson(Person person) {
        String query = "INSERT INTO Users (fname, lname, email, password, iv_base64) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setString(3, person.getEmail());
            stmt.setString(4, person.getPassword());
            stmt.setString(5, person.getIvBase64());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the user given their email
     * @param email The email of the specified person
     * @return The person that the email corresponds towards
     */
    @Override
    public Person getPersonByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        Person person = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                person = new Person(rs.getInt("user_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"), rs.getString("iv_base64") );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }


    /**
     * Retrieves a person that matches with the user ID
     * @param id The id of the contact to retrieve.
     * @return A person that corresponds to the user ID
     */
    @Override
    public Person getPerson(int id) {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        Person person = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                person = new Person(rs.getInt("user_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"), rs.getString("iv_base64"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    /**
     * Email validation to see if email already exists
     * @param email The email to be checked
     * @return
     */
    public boolean isEmailRegistered(String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // If the count is greater than 0, the email exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
