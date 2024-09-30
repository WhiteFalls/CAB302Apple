import People.Person;
import Tasks.Task;
import Tasks.taskCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private static final boolean DEBUG_MODE = false;  // Set this to true to disable tests

    @BeforeEach
    public void setup() {
        if (DEBUG_MODE) {
            Assumptions.assumeTrue(false, "Tests disabled in debug mode.");
        }

    }

    // im kinda tired fr fr
    @Test
    public void testPersonCreation() {
        Person person = new Person(0, "test", "user", "test@example.com", "encryptedPassword", "testIV");
        assertEquals("test", person.getFirstName());
        assertEquals("user", person.getLastName());
        assertEquals("test@example.com", person.getEmail());
        assertEquals("encryptedPassword", person.getPassword());
        assertEquals("testIV", person.getIvBase64());
    }

    @Test
    public void testSetEmail() {
        Person person = new Person(0, "test", "user", "test@example.com", "encryptedPassword", "testIV");
        person.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", person.getEmail());
    }

    @Test
    public void testPasswordEncryptionHandling() {
        Person person = new Person(0, "test", "user", "test@example.com", "encryptedPassword", "testIV");
        assertEquals("encryptedPassword", person.getPassword());
        person.setPassword("newEncryptedPassword");
        assertEquals("newEncryptedPassword", person.getPassword());
    }
}
