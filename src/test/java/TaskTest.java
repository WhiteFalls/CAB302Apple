import People.MockPerson;
import People.Person;
import Tasks.Task;
import Tasks.taskCategory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private MockPerson person;
    private Task task;

    @BeforeEach
    public void setUp() {
        person = new MockPerson("John", "Doe", "john.doe@example.com", "1234567890");
        Task task = new Task("task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
    }

    @Test
    public void testGetId() {
        task.setId(1);
        assertEquals(1, task.getId());
    }

    @Test
    public void testGetTaskDetails()
    {
        assertEquals("task", task.getTaskDetails());
    }

    @Test
    public void testGetTaskAssignedDate()
    {
        assertEquals(LocalDate.now(), task.getAssignedDate());
    }

    @Test
    public void testGetTaskDueDate()
    {
        assertEquals(LocalDate.now(), task.getDueDate());
    }

    @Test
    public void testGetCategory()
    {
        assertEquals(taskCategory.DAILY, task.getCategory());
    }
}
