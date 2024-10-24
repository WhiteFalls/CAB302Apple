package Database;

import GardenCell.Garden;
import People.IPerson;

import java.util.List;

public interface IGardenUsersDAO {

    /**
     * Gets all the IDs of each person in the garden
     * @param garden The garden to get the user IDs from
     * @return An array of all the IDs in the garden
     */
    List<Integer> getPeopleIdsInGarden(Garden garden);

    /**
     * Adss a single person to the garden
     * @param person The person to be added
     * @param garden The garden to add that person to
     * @param Role The person's particular role in the garden
     */
    void addPersonToGarden(IPerson person, Garden garden, String Role);

    /**
     * Removes a person from a garden
     * @param person The person to be removed
     * @param garden The garden to remove the person from
     */
    void removePersonFromGarden(IPerson person, Garden garden);

    /**
     * Gets all the gardens that a user belongs to
     * @param userId The user ID of the specified person
     * @return A List of all the gardens the person belongs to
     */
    List<Garden> getAllGardenByUserId(int userId);
}
