package Database;

import GardenCell.Plant;

import java.util.ArrayList;
import java.util.List;
public interface IPlantDAO {


    public Plant getPlantById(Plant plant);
    public ArrayList<Plant> getAllPlants();
    public ArrayList<Plant> getPlantContainsName(String userInput);
    Plant getPlantById(int plantId);
}


