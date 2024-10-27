package Database;

import GardenCell.Garden;
import Tasks.Task;

import java.util.List;

public interface IGardenDAO {
    /**
     * Adds a garden to the database
     * @param garden The garden to be added
     */
    void addGarden(Garden garden);


    /**
     * Gets the garden with a specified user ID
     * @param userId The ID of the user
     * @return The gardens with the specified user ID
     */
    Garden getGardenByUserId(int userId);

    /**
     * Updates the garden's details
     * @param garden The garden to be updated
     */
    void updateGarden(Garden garden);

    /**
     * Deletes the garden from the database
     * @param gardenId The ID of the garden to be deleted
     */
    void deleteGarden(int gardenId);
}
