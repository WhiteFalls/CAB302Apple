package Database;

import People.IPerson;
import People.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements IPersonDAO {

    private final Connection connection;

    // Constructor to initialize the database connection

    /**
     * Constructs a new GardenDAO and initializes the connection to the database
     * @param connection The connection to the database
     */
    public PersonDAO(Connection connection) {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void addPerson(Person person) {
        String query = "INSERT INTO Users (fname, lname, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setString(3, person.getEmail());
            stmt.setString(4, person.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Person getPersonByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        Person person = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                person = new Person(rs.getInt("user_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public void addPerson(IPerson person) {

    }

    @Override
    public void updatePerson(IPerson person) {

    }

    @Override
    public void deletePerson(IPerson person) {
        String query = "DELETE FROM Users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonFromGarden(IPerson person, int garden_ID) throws IllegalArgumentException {

    }

    @Override
    public Person getPerson(int id) {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        Person person = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                person = new Person(rs.getInt("user_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public ArrayList<IPerson> getAllPeople() {
        String query = "SELECT * FROM Users";
        ArrayList<IPerson> people = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            //stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Person person = new Person(rs.getInt("user_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
                if (person.getUserId() == 0)
                {
                    break;
                }
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }


    @Override
    public void deletePerson(Person person) {

    }
}
