package Database;

import GardenCell.Plant;

import java.util.ArrayList;
import java.util.List;
public interface IPlantDAO {

    /**
     * Gets all the plants in the database
     * @return An ArrayList of all the plants in the database
     */
    public ArrayList<Plant> getAllPlants();

    /**
     * Gets all that plants that contain a specified name/word
     * @param userInput The word that the plants need to contain
     * @return An ArrayList of all the plants that contain that word
     */
    public ArrayList<Plant> getPlantContainsName(String userInput);
}


