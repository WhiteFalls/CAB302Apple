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
     * Gets a garden with the specified garden ID
     * @param gardenId The ID of the garden
     * @return The garden with the specified ID
     */
    Garden getGardenById(int gardenId);

    /**
     * Gets all the gardens with a specified user ID
     * @param userId The ID of the user
     * @return The gardens with the specified user ID
     */
    List<Garden> getGardensByUserId(int userId);
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

    //List<Garden> getAllGardenByUserId(int userId);
    Garden getGardenByTaskId(Task task);
}
