package GardenCell;

import javafx.scene.paint.Color;

import java.time.LocalDate;

public interface IGardenCell {
    /**
     * Gets the plant stored in the cell
     * @return The name of the plant stored in the cell
     */
    String getPlant();

    /**
     * Sets the name of the plant stored in the cell
     * @param plant The name of the cell's plant
     */
    void setPlant(String plant);

    /**
     * Gets the x-coordinate of the garden cell
     * @return The x-coordinate of the cell
     */
    int getX();

    /**
     * Sets the x-coordinate of the cell
     * @param x The x-coordinate of the cell
     */
    void setX(int x);

    /**
     * Gets the y-coordinate of the garden cell
     * @return The y-coordinate of the cell
     */
    int getY();

    /**
     * Sets the y-coordinate of the cell
     * @param y The y-coordinate of the cell
     */
    void setY(int y);

    /**
     * Gets the date that the plant has planted
     * @return A LocalDate of the planted date
     */
    LocalDate getPlantedDate();

    /**
     * Sets that day when the plant was planted
     * @param date The planted date of the plant
     */
    void setPlantedDate(LocalDate date);

    /**
     * Gets the date that the plant is set to be harvested
     * @return A LocalDate of the hrvest date
     */
    LocalDate getHarvestDate();

    /**
     * Sets that day when the plant is to be harvested
     * @param date The harvest date of the plant
     */
    void setHarvestDate(LocalDate date);

    /**
     * Gets the background colour of the cell
     * @return The colour value of the cell
     */
    Color getColour();

    /**
     * Sets the background colour of the cell
     * @param colour The colour value that the cell will be coloured in
     */
    void setColour(Color colour);
}
