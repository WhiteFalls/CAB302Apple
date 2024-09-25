package People;

public class Garden {
    private int gardenId;
    private int ownerId;
    private int width;
    private int height;
    private String gardenName;

    // Constructor for new garden creation
    public Garden(int ownerId, String gardenName) {
        this.ownerId = ownerId;
        this.gardenName = gardenName;
    }

    // Constructor when loading from database
    public Garden(int gardenId, int ownerId, int width, int height, String gardenName) {
        this.gardenId = gardenId;
        this.ownerId = ownerId;
        this.width = width;
        this.height = height;
        this.gardenName = gardenName;
    }

    // Getters and Setters
    public int getGardenId() {
        return gardenId;
    }

    public int getOwnerId() {
        return ownerId;
    }

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

    public void setHeight(int width)
    {
        this.height = height;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }
}
