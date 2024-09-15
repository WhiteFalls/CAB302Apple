import People.Person;
import Tasks.Task;
import com.example.gardenplanner.controller.GardenManagementController;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GardenManagementControllerTest {
    GardenManagementController controller;

    @BeforeEach
    public void setUp() {
        controller = new GardenManagementController();
    }
}
