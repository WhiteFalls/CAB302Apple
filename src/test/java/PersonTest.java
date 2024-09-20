import People.Person;
import Tasks.Task;
import Tasks.taskCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

public class PersonTest {
    private Person person;
    private Task task;

    @BeforeEach
    public void setUp() {
        person = new Person("John", "Doe", "john.doe@example.com", "1234567890");
        task = new Task(1, "task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
    }

    @org.junit.jupiter.api.Test
    public void testGetId() {
        person.setUserId(1);
        assertEquals(1, person.getUserId());
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
    public void testSetPassword() {
        person.setPassword("0987654321");
        assertEquals("0987654321", person.getPassword());
    }

    @org.junit.jupiter.api.Test
    public void testGetName() {
        assertEquals("John Doe", person.getName());
    }

    @org.junit.jupiter.api.Test
    public void testAddTask() {
        person.addTask(task);
        assertEquals(task, person.getTasks()[0]);
    }

    @org.junit.jupiter.api.Test
    public void testEditTask() {
        person.addTask(task);

        Task new_task = new Task(2, "new_task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        person.editTask(new_task, task);
        assertEquals(new_task, person.getTasks()[0]);
    }

    @org.junit.jupiter.api.Test
    public void testRemoveTask() {
        person.addTask(task);
        person.removeTask(task.getId());
        assertTrue(person.getTasks().length == 0);

    }

}
