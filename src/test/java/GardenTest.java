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

    @Test
    public void testGetMethods() {
        assertEquals(1, garden.getGardenId());
        assertEquals("Garden", garden.getGardenName());
        assertEquals(2, garden.getOwnerId());
        assertEquals(5, garden.getWidth());
        assertEquals(5, garden.getHeight());
    }

    @Test
    public void testSetMethods() {
        garden.setGardenId(2);
        garden.setGardenName("new garden");
        garden.setWidth(10);
        garden.setHeight(10);

        assertEquals(2, garden.getGardenId());
        assertEquals("new garden", garden.getGardenName());
        assertEquals(10, garden.getWidth());
        assertEquals(10, garden.getHeight());
    }
}