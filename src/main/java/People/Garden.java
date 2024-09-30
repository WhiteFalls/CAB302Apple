package People;

public class Garden {
    private int gardenId;
    private int ownerId;
    private int width;
    private int height;
    private String gardenName;

    // Constructor for new garden creation


    // Constructor when loading from database

    /**
     * Constructs a new garden object
     * @param ownerId The user ID of the owner/manager of the garden
     * @param gardenName The name of the garden
     */
    public Garden(String gardenName, int ownerId, int width, int height) {
        this.ownerId = ownerId;
        this.width = width;
        this.height = height;
        this.gardenName = gardenName;
    }

    public Garden(int gardenId, String gardenName, int ownerId, int width, int height) {
        this.gardenId = gardenId;
        this.ownerId = ownerId;
        this.width = width;
        this.height = height;
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

    public void setGardenId(int gardenId)
    {
        this.gardenId = gardenId;
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

    public int getWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Sets the name of the garden
     * @param gardenName The new name of the Garden
     */
    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }
}
