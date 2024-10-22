import People.Person;
import Tasks.Task;
import Tasks.taskCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private static final boolean DEBUG_MODE = false;  // Set this to true to disable tests
    private Person person;
    private Task task;

    @BeforeEach
    public void setup() {
        if (DEBUG_MODE) {
            Assumptions.assumeTrue(false, "Tests disabled in debug mode.");
        }

        person = new Person(0, "test", "user", "test@example.com", "encryptedPassword", "testIV");


    }

    // im kinda tired fr fr
    @Test
    public void testGetMethods() {
        assertEquals(0, person.getUserId());
        assertEquals("test", person.getFirstName());
        assertEquals("user", person.getLastName());
        assertEquals("test user", person.getName());
        assertEquals("test@example.com", person.getEmail());
        assertEquals("encryptedPassword", person.getPassword());
        assertEquals("testIV", person.getIvBase64());
    }

    @Test
    public void testSetMethods() {
        person.setUserId(1);
        person.setFirstName("new test");
        person.setLastName("new user");
        person.setEmail("newtest@example.com");
        person.setPassword("newEncryptedPassword");
        person.setIvBase64("testV");

        assertEquals("new test", person.getFirstName());
        assertEquals("new user", person.getLastName());
        assertEquals("newtest@example.com", person.getEmail());
        assertEquals("newEncryptedPassword", person.getPassword());
        assertEquals("testV", person.getIvBase64());
    }

    @Test
    public void testAddAndGetNewestTask()
    {
        Task task = new Task(1, "task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        person.addTask(task);
        Task[] taskList = {task};
        assertEquals(taskList[0], person.getNewestTask());
    }

    @Test
    public void testGetNewestTaskFromEmptyList()
    {
        assertNull(person.getNewestTask());
    }

    @Test
    public void testAddingMultipleTasks()
    {
        Task task1 = new Task(1, "task1", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        Task task2 = new Task(2, "task2", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);

        person.addTask(task1);
        person.addTask(task2);
        Task[] taskList = {task1, task2};
        assertEquals(taskList[0], person.getTasks()[0]);
        assertEquals(taskList[1], person.getTasks()[1]);

    }

    @Test
    public void testSettingTasks()
    {
        Task task1 = new Task(1, "task1", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        Task task2 = new Task(2, "task2", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        person.setTasks(taskList);
        assertEquals(taskList.toArray()[0], person.getTasks()[0]);

    }

    @Test
    public void testEditingTasks()
    {
        Task task1 = new Task(1, "task1", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        Task task2 = new Task(1, "task2", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task1);

        person.setTasks(taskList);
        person.editTask(task2, task1);
        assertEquals(task2, person.getNewestTask());

    }

    @Test
    public void testRemovingTasks()
    {
        Task task1 = new Task(1, "task1", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        Task task2 = new Task(1, "task2", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task1);

        person.setTasks(taskList);
        person.removeTask(task1.getId());
        assertNull(person.getNewestTask());

    }

    @Test
    public void testRemovingTasksWithMultipleTasks()
    {
        Task task1 = new Task(1, "task1", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        Task task2 = new Task(2, "task2", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        person.setTasks(taskList);
        person.removeTask(task1.getId());
        assertEquals(task2, person.getNewestTask());
    }

    @Test
    public void testEditingTasksWithInvalidIndex()
    {
        Task task1 = new Task(1, "task1", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        Task task2 = new Task(1, "task2", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task1);

        person.setTasks(taskList);
        person.editTask(task2, task1);
        assertEquals(task2, person.getNewestTask());

    }



    @Test
    public void testSetEmail() {
        person.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", person.getEmail());
    }

    @Test
    public void testPasswordEncryptionHandling() {
        assertEquals("encryptedPassword", person.getPassword());
        person.setPassword("newEncryptedPassword");
        assertEquals("newEncryptedPassword", person.getPassword());
    }
}
