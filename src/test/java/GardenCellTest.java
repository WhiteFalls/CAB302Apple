import GardenCell.GardenCell;
import Tasks.Task;
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
        GardenCell cell = new GardenCell("Plant", 5, 6, LocalDate.now(), LocalDate.now(), Color.BLUE);
    }

    @org.junit.jupiter.api.Test
    public void testGetPlant() {
        assertEquals("Plant", cell.getPlant());
    }

    @org.junit.jupiter.api.Test
    public void testGetX() {
        assertEquals(5, cell.getY());
    }

    @org.junit.jupiter.api.Test
    public void testGetY() {
        assertEquals(6, cell.getY());
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
        assertEquals(Color.BLUE.toString(), cell.getColour().toString());
    }

}