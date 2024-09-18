import Database.PersonDAO;
import People.Person;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
//org.junit.jupiter:junit-jupiter:RELEASE

public class PersonDAOTest {

    private static Connection connection;
    private PersonDAO personDAO;

    @BeforeAll
    public static void setupDatabaseConnection() throws SQLException {
        // Set up a test connection (using temporary test database)
        connection = DriverManager.getConnection("jdbc:sqlite:test-database.sqlite");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS Users ("
                + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "fname TEXT NOT NULL, "
                + "lname TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "password TEXT NOT NULL);");
    }

    @AfterAll
    public static void closeDatabaseConnection() throws SQLException {
        // Close the connection after tests
        if (connection != null) {
            connection.close();
        }
    }

    @BeforeEach
    public void setUp() {
        // Initialize DAO before each test
        personDAO = new PersonDAO(connection);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        // Clear the table after each test to avoid conflicts
        connection.createStatement().execute("DELETE FROM Users");
    }

    @Test
    public void testAddPerson() throws SQLException {
        Person person = new Person("John", "Doe", "john.doe@example.com", "password123");
        personDAO.addPerson(person);

        Person fetchedPerson = personDAO.getPersonByEmail("john.doe@example.com");

        assertNotNull(fetchedPerson);
        assertEquals("John", fetchedPerson.getFirstName());
        assertEquals("Doe", fetchedPerson.getLastName());
        assertEquals("john.doe@example.com", fetchedPerson.getEmail());
    }

    @Test
    public void testGetPersonByEmail() throws SQLException {
        Person person = new Person("Jane", "Smith", "jane.smith@example.com", "password456");
        personDAO.addPerson(person);

        Person fetchedPerson = personDAO.getPersonByEmail("jane.smith@example.com");

        assertNotNull(fetchedPerson);
        assertEquals("Jane", fetchedPerson.getFirstName());
        assertEquals("Smith", fetchedPerson.getLastName());
        assertEquals("jane.smith@example.com", fetchedPerson.getEmail());
    }
}
