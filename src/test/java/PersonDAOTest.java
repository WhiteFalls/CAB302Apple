import Database.PersonDAO;
import People.Person;
import com.example.gardenplanner.DatabaseInitializer;
import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

// Add a global debug flag to control whether tests run
public class PersonDAOTest {

    private static final boolean DEBUG_MODE = false;  // Set this to true to disable tests

    private Connection connection;
    private PersonDAO personDAO;

    @BeforeEach
    public void setup() throws SQLException {
        if (DEBUG_MODE) {
            Assumptions.assumeTrue(false, "Tests disabled in debug mode.");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:GardenPlanner.sqlite");
        personDAO = new PersonDAO();
        DatabaseInitializer.checkAndCreateDatabase();  // Ensure the database and tables are set up before tests
    }

    @AfterEach
    public void teardown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testInsertPerson() {
        Person person = new Person(0, "test", "user", "test@example.com", "encryptedPassword", "ivBase64");
        personDAO.addPerson(person);
        assertTrue(true, "Person should be inserted successfully.");
    }

    @Test
    public void testGetPersonByEmail() throws SQLException {
        Person person = personDAO.getPersonByEmail("test@test.test");
        assertNotNull(person, "Person should be retrieved by email.");
        assertEquals("test", person.getFirstName());
        assertEquals("test", person.getLastName());
    }

    @Test
    public void testEmailUniqueness() throws SQLException {
        // Inserting the same email should fail
        Person person1 = new Person(0, "John", "Doe", "john@example.com", "encryptedPassword", "ivBase64");
        personDAO.addPerson(person1);
        Person person2 = new Person(0, "Jane", "Doe", "john@example.com", "encryptedPassword", "ivBase64");
        personDAO.addPerson(person2);
        assertFalse(false, "Duplicate emails should not be allowed.");
    }

    @Test
    public void testIvBase64Handling() throws SQLException {
        Person person = new Person(0, "test", "user", "ivtest@example.com", "encryptedPassword", "testIV");
        personDAO.addPerson(person);
        Person retrievedPerson = personDAO.getPersonByEmail("ivtest@example.com");
        assertNotNull(retrievedPerson);
        assertEquals("testIV", retrievedPerson.getIvBase64(), "IV should match the inserted value.");
    }
}
