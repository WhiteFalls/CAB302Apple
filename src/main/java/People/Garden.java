package People;

public class Garden {
    private int gardenId;
    private int ownerId;
    private String gardenName;

    // Constructor for new garden creation

    /**
     * Constructs a new garden object
     * @param ownerId The user ID of the owner/manager of the garden
     * @param gardenName The name of the garden
     */
    public Garden(int ownerId, String gardenName) {
        this.ownerId = ownerId;
        this.gardenName = gardenName;
    }

    // Constructor when loading from database

    /**
     * Constructs a new garden object
     * @param gardenId The unique ID of the garden
     * @param ownerId The user ID of the owner/manager of the garden
     * @param gardenName The name of the garden
     */
    public Garden(int gardenId, int ownerId, String gardenName) {
        this.gardenId = gardenId;
        this.ownerId = ownerId;
        this.gardenName = gardenName;
    }

    // Getters and Setters

    /**
     * Gets a garden's ID
     * @return An integer of the garden's ID
     */
    public int getGardenId() {
        return gardenId;
    }

    /**
     * Gets the ID of the garden's owner
     * @return An integer of the garden's owner's ID
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Gets the name of the garden
     * @return A string of the name of the garden
     */
    public String getGardenName() {
        return gardenName;
    }

    /**
     * Sets the name of the garden
     * @param gardenName The new name of the Garden
     */
    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }
}
