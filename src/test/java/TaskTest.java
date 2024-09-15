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

    //@Test
    //testContructorInvalidArgument
}
