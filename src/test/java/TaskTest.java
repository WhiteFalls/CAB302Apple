import Tasks.Task;
import Tasks.taskCategory;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task(rs.getInt("task_id"), "task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
    }

    @org.junit.jupiter.api.Test
    public void testGetId() {
        task.setId(1);
        assertEquals(1, task.getId());
    }

    @org.junit.jupiter.api.Test
    public void testGetTaskDetails()
    {
        assertEquals("task", task.getTaskDetails());
    }

    @org.junit.jupiter.api.Test
    public void testGetTaskAssignedDate()
    {
        assertEquals(LocalDate.now(), task.getAssignedDate());
    }

    @org.junit.jupiter.api.Test
    public void testGetTaskDueDate()
    {
        assertEquals(LocalDate.now(), task.getDueDate());
    }

    @org.junit.jupiter.api.Test
    public void testGetCategory()
    {
        assertEquals(taskCategory.DAILY, task.getCategory());
    }
}
