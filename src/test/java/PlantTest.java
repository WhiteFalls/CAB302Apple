import GardenCell.Garden;
import GardenCell.Plant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlantTest {
    private Plant plant;

    @BeforeEach
    public void setUp() {
        plant = new Plant(1, "Plant", "A plant",
                "Every day", "Direct sunlight");
    }

    @Test
    public void testGetMethods() {
        assertEquals(1, plant.getPlantID());
        assertEquals("Plant", plant.getPlantName());
        assertEquals("A plant", plant.getPlantDescription());
        assertEquals("Every day", plant.getWatering());
        assertEquals("Direct sunlight", plant.getOptimalSunlight());
    }
}
