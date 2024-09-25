package Database;

import GardenCell.GardenCell;
import People.Garden;

public interface IGardenMapDAO {
    public void createDefaultMap(Garden garden);

    public GardenCell[][] getGardenCells(Garden garden);

    public void updateCell(GardenCell cell);

    public void resizeMap(Garden garden);
}
