package GardenCell;
import Database.PlantDAO;
import Database.IPlantDAO;

public class Plant {
    private int plantId;
    private String plantName;
    private String plantDescription;
    private String watering;
    private String optimalSunlight;


    public Plant(int plantId, String plantName, String plantDescription, String waterRequirements, String optimalSunlight) {
        this.plantId = plantId;
        this.plantName = plantName;
        this.plantDescription = plantDescription;
        this.watering = waterRequirements;
        this.optimalSunlight = optimalSunlight;
    }

    public int getPlantID() {
    }
}
