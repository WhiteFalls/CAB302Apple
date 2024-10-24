package GardenCell;

import javafx.scene.paint.Color;
import java.time.LocalDate;

public class GardenCell implements IGardenCell {
    private int X;
    private int Y;
    private Color colour;
    private String plant;
    private LocalDate plantedDate;
    private LocalDate harvestDate;

    /**
     * Constructs a new GardenCell object with the specified plant, x and y coordinates, planted and
     * harvest dates and colour
     * @param plant The plant stored in the cell
     * @param x The x-coordinate of the cell
     * @param y The y-coordinate of the cell
     * @param plantedDate The date the plant was planted
     * @param harvestDate The date the plant will be harvested
     * @param colour The background colour of the cell
     */
    public GardenCell(String plant, int x, int y, LocalDate plantedDate, LocalDate harvestDate, Color colour)
    {
        this.plant = plant;
        this.X = x;
        this.Y = y;
        this.plantedDate = plantedDate;
        this.harvestDate = harvestDate;
        this.colour = colour;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public LocalDate getPlantedDate() {
        return plantedDate;
    }

    public void setPlantedDate(LocalDate date) {
        this.plantedDate = date;
    }

    public LocalDate getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(LocalDate date) {
        this.harvestDate = date;
    }

    public Color getColour()
    {
        return this.colour;
    }

    public void setColour(Color colour)
    {
        this.colour = colour;
    }
}
