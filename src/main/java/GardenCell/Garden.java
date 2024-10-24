package GardenCell;

public class Garden implements IGarden {
    private int gardenId;
    private int ownerId;
    private int width;
    private int height;
    private String gardenName;

    // Constructor for new garden creation


    // Constructor when loading from database

    /**
     * Constructs a new garden object
     * @param gardenName The name of the garden
     * @param ownerId The user ID of the owner/manager of the garden
     * @param width How many plots make up the width of the garden
     * @param height How many plots make up the height of the garden
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

    @Override
    public int getGardenId() {
        return gardenId;
    }

    @Override
    public void setGardenId(int gardenId)
    {
        this.gardenId = gardenId;
    }

    @Override
    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public String getGardenName() {
        return gardenName;
    }

    @Override
    public int getWidth()
    {
        return this.width;
    }

    @Override
    public void setWidth(int width)
    {
        this.width = width;
    }

    @Override
    public int getHeight()
    {
        return this.height;
    }

    @Override
    public void setHeight(int height)
    {
        this.height = height;
    }

    @Override
    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }
}
