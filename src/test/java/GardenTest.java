import GardenCell.Garden;
import Tasks.taskCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GardenTest {
    private Garden garden;

    @BeforeEach
    public void setUp() {
        garden = new Garden(1, "Garden", 2, 5, 5);
    }

    @org.junit.jupiter.api.Test
    public void testGetId() {
        garden.setGardenId(10);
        assertEquals(10, garden.getGardenId());
    }

    @org.junit.jupiter.api.Test
    public void testGetGardenName() {
        assertEquals("Garden", garden.getGardenName());
    }

    @org.junit.jupiter.api.Test
    public void testGetOwnerID() {
        assertEquals(2, garden.getOwnerId());
    }

    @org.junit.jupiter.api.Test
    public void testGetHeight() {
        assertEquals(5, garden.getHeight());
    }

    @org.junit.jupiter.api.Test
    public void testGetWidth() {
        assertEquals(5, garden.getWidth());
    }
}