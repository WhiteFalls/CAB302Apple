package Database;

import GardenCell.GardenCell;
import GardenCell.Garden;

public interface IGardenMapDAO {

    /**
     * Creates a default map for when the person first generates their garden
     * @param garden The newly generated garden
     */
    public void createDefaultMap(Garden garden);

    /**
     * Gets all the cells in the garden formated into their appropriate rows and columns
     * @param garden The garden to get the cells from
     * @return A 2D array of all the cells in the garden
     */
    public GardenCell[][] getGardenCells(Garden garden);

    /**
     * Updates an individual cell in the garden
     * @param cell The new cell with the same x and y coordinates as the old cell
     */
    public void updateCell(GardenCell cell);

    /**
     * Resizes the garden to the specified size
     * @param garden The garden to be resized
     * @param newWidth The new width of the garden
     * @param newHeight The new height of the garden
     */
    public void resizeMap(Garden garden, int newWidth, int newHeight);
}
