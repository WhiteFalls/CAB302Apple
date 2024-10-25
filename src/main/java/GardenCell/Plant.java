package GardenCell;
import Database.PlantDAO;
import Database.IPlantDAO;

public class Plant implements IPlant {
    private int plantId;
    private String plantName;
    private String plantDescription;
    private String watering;
    private String optimalSunlight;

    /**
     * Contructs a new Plant object with the specified ID, name, description, and watering and sunlight information
     *
     * @param plantId
     * @param plantName
     * @param plantDescription
     * @param watering
     * @param optimalSunlight
     */
    public Plant(int plantId, String plantName, String plantDescription, String watering, String optimalSunlight) {
        this.plantId = plantId;
        this.plantName = plantName;
        this.plantDescription = plantDescription;
        this.watering = watering;
        this.optimalSunlight = optimalSunlight;
    }

    public int getPlantID() {
        return plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getPlantDescription() {
        return plantDescription;
    }

    public String getWatering() {
        return watering;
    }

    public String getOptimalSunlight() {
        return optimalSunlight;
    }
}
