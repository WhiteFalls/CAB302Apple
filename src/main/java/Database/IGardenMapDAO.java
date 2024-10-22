package Database;

import GardenCell.GardenCell;
import GardenCell.Garden;

public interface IGardenMapDAO {
    public void createDefaultMap(Garden garden);

    public GardenCell[][] getGardenCells(Garden garden);

    public void updateCell(GardenCell cell);

    public void resizeMap(Garden garden, int newWidth, int newHeight);
}
