package Database;

import People.Garden;

import java.util.List;

public interface IGardenDAO {
    void addGarden(Garden garden);
    Garden getGardenById(int gardenId);
    List<Garden> getGardensByUserId(int userId);
    Garden getGardenByUserId(int userId);
    void updateGarden(Garden garden);
    void deleteGarden(int gardenId);
}
