package GardenCell;

import javafx.scene.paint.Color;
import java.time.LocalDate;

public class GardenCell {
    private int X;
    private int Y;
    private Color colour;
    private String plant;
    private LocalDate plantedDate;
    private LocalDate harvestDate;

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
