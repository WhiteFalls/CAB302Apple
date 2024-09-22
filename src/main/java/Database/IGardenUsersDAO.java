package Database;

import People.Garden;
import People.IPerson;
import People.Person;

import java.util.List;

public interface IGardenUsersDAO {
    List<Integer> getPeopleIdsInGarden(Garden garden);

    void addPersonToGarden(IPerson person, Garden garden);

    void removePersonFromGarden(IPerson person, Garden garden);

    List<Garden> getAllGardenByUserId(int userId);
}
