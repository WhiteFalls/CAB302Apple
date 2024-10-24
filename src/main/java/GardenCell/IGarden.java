package GardenCell;

public interface IGarden {
    /**
     * Gets a garden's ID
     *
     * @return An integer of the garden's ID
     */
    int getGardenId();

    /**
     * Sets the ID of the garden
     * @param gardenId The unique ID value of the garden
     */
    void setGardenId(int gardenId);

    /**
     * Gets the ID of the garden's owner
     *
     * @return An integer of the garden's owner's ID
     */
    int getOwnerId();

    /**
     * Gets the name of the garden
     *
     * @return A string of the name of the garden
     */
    String getGardenName();

    /**
     * Gets how many plots make up the width of the garden
     * @return The garden's width value
     */
    int getWidth();

    /**
     * Sets how many plots make up the width of the garden
     * @param width The width of the garden
     */
    void setWidth(int width);

    /**
     * Gets how many plots make up the height of the garden
     * @return The garden's height value
     */
    int getHeight();

    /**
     * Sets how many plots make up the height of the garden
     * @param height The height of the garden
     */
    void setHeight(int height);

    /**
     * Sets the name of the garden
     * @param gardenName The new name of the Garden
     */
    void setGardenName(String gardenName);
}
