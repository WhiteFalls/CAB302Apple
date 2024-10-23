import People.Person;
import Tasks.Task;
import Tasks.taskCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task(1, "task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
    }


    @Test
    public void testGetMethods() {
        assertEquals(1, task.getId());
        assertEquals("task", task.getTaskDetails());
        assertEquals(LocalDate.now(), task.getAssignedDate());
        assertEquals(LocalDate.now(), task.getDueDate());
        assertEquals(taskCategory.DAILY, task.getCategory());
    }

    @Test
    public void testSetMethods() {
        task.setId(2);
        task.setAssignedDate(LocalDate.EPOCH);
        task.setDueDate(LocalDate.EPOCH);
        task.setCategory(taskCategory.WEEKLY);

        assertEquals(2, task.getId());
        assertEquals(LocalDate.EPOCH, task.getAssignedDate());
        assertEquals(LocalDate.EPOCH, task.getDueDate());
        assertEquals(taskCategory.WEEKLY, task.getCategory());
    }

    @Test
    public void testGetHashCode()
    {
        int hash = Objects.hash(task.getId(), task.getTaskDetails());
        assertEquals(hash, task.hashCode());
    }

    @Test
    public void testTaskEquals() {
        Task sametask = new Task(1, "task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        assertTrue(task.equals(sametask));
    }

    @Test
    public void testTaskEqualsSameObject() {
        assertTrue(task.equals(task));
    }

    @Test
    public void testTaskEqualsNull() {
        assertFalse(task.equals(null));
    }

    @Test
    public void testTaskEqualsDifferentClass() {
        Person person = new Person("test", "user", "test@gmail.com",
                "Password", "testIV");
        assertFalse(task.equals(person));
    }

    @Test
    public void testUpdateTask() {
        Task newTask = new Task(2, "new task", LocalDate.EPOCH, LocalDate.EPOCH, taskCategory.WEEKLY);
        task.updateTask(newTask);

        assertEquals(task.getTaskDetails(), newTask.getTaskDetails());
        assertEquals(task.getAssignedDate(), newTask.getAssignedDate());
        assertEquals(task.getDueDate(), newTask.getDueDate());
        assertEquals(task.getCategory(), newTask.getCategory());
    }
}
