import People.MockPerson;
import People.Person;
import Tasks.Task;
import Tasks.taskCategory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private MockPerson person;
    private Task task;

    @BeforeEach
    public void setUp() {
        person = new MockPerson("John", "Doe", "john.doe@example.com", "1234567890");
        Task task = new Task("task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
    }

    @org.junit.jupiter.api.Test
    public void testGetId() {
        person.setId(1);
        assertEquals(1, person.getId());
    }

    @org.junit.jupiter.api.Test
    public void testGetFirstName() {
        assertEquals("John", person.getFirstName());
    }

    @org.junit.jupiter.api.Test
    public void testSetFirstName() {
        person.setFirstName("Jane");
        assertEquals("Jane", person.getFirstName());
    }

    @org.junit.jupiter.api.Test
    public void testGetLastName() {
        assertEquals("Doe", person.getLastName());
    }

    @org.junit.jupiter.api.Test
    public void testSetLastName() {
        person.setLastName("Smith");
        assertEquals("Smith", person.getLastName());
    }

    @org.junit.jupiter.api.Test
    public void testGetEmail() {
        assertEquals("john.doe@example.com", person.getEmail());
    }

    @org.junit.jupiter.api.Test
    public void testSetEmail() {
        person.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", person.getEmail());
    }

    @org.junit.jupiter.api.Test
    public void testGetPassword() {
        assertEquals("1234567890", person.getPassword());
    }

    @org.junit.jupiter.api.Test
    public void testSetPhone() {
        person.setPassword("0987654321");
        assertEquals("0987654321", person.getPassword());
    }

    @org.junit.jupiter.api.Test
    public void testGetFullName() {
        assertEquals("John Doe", person.getName());
    }

    @Test
    public void testAddTask() {
        person.addTask(task);
        assertEquals(task, person.getTasks().get(0));
    }

    @Test
    public void testEditTask() {
        person.addTask(task);

        Task new_task = new Task("new_task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        person.editTask(new_task, task);
        assertEquals(new_task, person.getTasks().get(0));
    }

    @Test
    public void testRemoveTask() {
        person.addTask(task);
        person.removeTask(task);
        assertTrue(person.getTasks().isEmpty());

    }

}
