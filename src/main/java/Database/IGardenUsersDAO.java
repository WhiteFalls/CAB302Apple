package Database;

import GardenCell.Garden;
import People.IPerson;

import java.util.List;

public interface IGardenUsersDAO {
    List<Integer> getPeopleIdsInGarden(Garden garden);

    void addPersonToGarden(IPerson person, Garden garden, String Role);

    void removePersonFromGarden(IPerson person, Garden garden);

    List<Garden> getAllGardenByUserId(int userId);
}
