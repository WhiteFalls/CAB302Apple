import GardenCell.GardenCell;
import Tasks.taskCategory;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GardenCellTest {
    private GardenCell cell;

    @BeforeEach
    public void setUp() {
        cell = new GardenCell("Vegetable", 2, 3, LocalDate.now(), LocalDate.now(), Color.BLUE);
    }

    @Test
    public void testGetMethods() {
        assertEquals("Vegetable", cell.getPlant());
        assertEquals(2, cell.getX());
        assertEquals(3, cell.getY());
        assertEquals(LocalDate.now(), cell.getPlantedDate());
        assertEquals(LocalDate.now(), cell.getHarvestDate());
        assertEquals(Color.BLUE, cell.getColour());
    }

    @Test
    public void testSetMethods() {
        cell.setPlant("New Vegetable");
        cell.setX(10);
        cell.setY(12);
        cell.setPlantedDate(LocalDate.EPOCH);
        cell.setHarvestDate(LocalDate.EPOCH);
        cell.setColour(Color.PERU);

        assertEquals("New Vegetable", cell.getPlant());
        assertEquals(10, cell.getX());
        assertEquals(12, cell.getY());
        assertEquals(LocalDate.EPOCH, cell.getPlantedDate());
        assertEquals(LocalDate.EPOCH, cell.getHarvestDate());
        assertEquals(Color.PERU, cell.getColour());
    }
}