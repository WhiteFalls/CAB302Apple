package GardenCell;

public interface IPlant {
    /**
     * Gets the unique ID of the plant
     * @return The value of the plant's ID
     */
    int getPlantID();

    /**
     * Gets the name of the plant
     * @return The plant's name
     */
    String getPlantName();

    /**
     * Gets a brief description of the plant's details
     * @return The description of the plant
     */
    String getPlantDescription();

    /**
     * Gets the watering information about the plant
     * @return The plant's watering information
     */
    String getWatering();

    /**
     * Gets the required sunlight information about the plant
     * @return The plant's sunlight needs
     */
    String getOptimalSunlight();
}
