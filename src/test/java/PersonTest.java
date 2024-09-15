import People.Person;
import Tasks.Task;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {
    private Person person;
    private Task task;

    @BeforeEach
    public void setUp() {
        person = new Person("John", "Doe", "john.doe@example.com", "1234567890");
        Task task = new Task("task", LocalDate.now(), LocalDate.now());

    }

    @Test
    public void testAddTask() {
        person.addTask(task);
        assertEquals(task, person.getTasks().get(0));
    }

    @Test
    public void testEditTask() {
        person.addTask(task);

        Task new_task = new Task("new_task", LocalDate.now(), LocalDate.now());
        person.editTask(new_task, task);
        assertEquals(new_task, person.getTasks().get(0));
    }

}
