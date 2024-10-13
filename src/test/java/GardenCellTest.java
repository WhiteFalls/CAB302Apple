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

    @org.junit.jupiter.api.Test
    public void testGetPlant() {
        assertEquals("Vegetable", cell.getPlant());
    }

    @org.junit.jupiter.api.Test
    public void testGetX() {
        assertEquals(2, cell.getX());
    }

    @org.junit.jupiter.api.Test
    public void testGetY() {
        assertEquals(3, cell.getY());
    }

    @org.junit.jupiter.api.Test
    public void testGetPlantedDate() {
        assertEquals(LocalDate.now(), cell.getPlantedDate());
    }

    @org.junit.jupiter.api.Test
    public void testGetHarvestDate() {
        assertEquals(LocalDate.now(), cell.getHarvestDate());
    }

    @org.junit.jupiter.api.Test
    public void testGetColour() {
        assertEquals(Color.BLUE, cell.getColour());
    }
}