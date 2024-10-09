package Database;

import GardenCell.Plant;
import java.util.List;
public interface IPlantDAO {


    public Plant getPlantById(Plant plant);

    Plant getPlantById(int plantId);
}


