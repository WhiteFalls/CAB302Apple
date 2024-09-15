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
