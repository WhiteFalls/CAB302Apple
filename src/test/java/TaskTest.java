import People.Person;
import Tasks.Task;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private Person person;
    private Task task;

    @BeforeEach
    public void setUp() {
        person = new Person("John", "Doe", "john.doe@example.com", "1234567890");
        Task task = new Task("task", LocalDate.now(), LocalDate.now());
    }

    @Test
    testContructorInvalidArgument
}
