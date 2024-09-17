package Database;

import People.Person;

import java.sql.*;
import java.util.ArrayList;

public class PersonDAO implements IPersonDAO {

    private Connection connection;

    // Constructor to initialize the database connection
    public PersonDAO() {
        connection = DatabaseConnection.getConnection();  // Assumes a utility class to manage DB connection
    }

    @Override
    public void addPerson(Person person) {
        String query = "INSERT INTO Person (name, email) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getEmail());
            stmt.executeUpdate();  // Execute the query to add the person
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPerson(IMockPerson mockPerson) {

    }

    @Override
    public void updatePerson(IMockPerson person) {

    }

    @Override
    public void deletePerson(IMockPerson person) {

    }

    @Override
    public Person getPerson(int id) {
        String query = "SELECT * FROM Person WHERE id = ?";
        Person person = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Create a new Person object from the result
                person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;  // Return the person object (or null if not found)
    }

    @Override
    public ArrayList<IMockPerson> getAllPeople() {
        return null;
    }

    // Method to retrieve a person by email
    public Person getPersonByEmail(String email) {
        String query = "SELECT * FROM Person WHERE email = ?";
        Person person = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Create a new Person object from the result
                person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }
}
