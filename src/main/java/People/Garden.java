package People;

public class Garden {
    private int gardenId;
    private int ownerId;
    private String gardenName;

    // Constructor for new garden creation
    public Garden(int ownerId, String gardenName) {
        this.ownerId = ownerId;
        this.gardenName = gardenName;
    }

    // Constructor when loading from database
    public Garden(int gardenId, int ownerId, String gardenName) {
        this.gardenId = gardenId;
        this.ownerId = ownerId;
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

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }
}
