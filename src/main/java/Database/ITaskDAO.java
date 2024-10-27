package Database;

import GardenCell.Garden;
import People.IPerson;
import Tasks.Task;
import Tasks.taskCategory;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ITaskDAO {
    /**
     * Deletes a task from the database
     * @param task The task to be deleted
     */
    public void delete(Task task);

    public void deleteUserTasks(IPerson person, Garden garden);

        /**
         * Updates a task in the database
         * @param oldTask The task to be updated
         * @param newTask The task that will replace oldTask
         */
    public void update(Task oldTask, Task newTask);

    /**
     * Add a task to the database
     * @param task The task to be added
     * @param person The person to add the task to
     */
    public void add(Task task, IPerson person, Garden garden);

    /**
     * Returns all a user's tasks in a garden with the specified category
     * @param person The person who owns the tasks
     * @param category The category to group the tasks
     * @param garden The garden that the tasks belong to
     * @return An ArrayList of all the users tasks in the garden that have the category
     */
    ArrayList<Task> getCategorisedTasksFromGarden(IPerson person, taskCategory category, Garden garden);


    /**
     * Updates the information of a task using its task ID
     * @param task The tasks containing the new information and the same ID as the task to be replaced
     */
    void update(Task task);

    /**
     * Deletes a task with a certain ID
     * @param id The ID of the task to be deleted
     */
    void delete(int id);

    /**
     * Gets all the users tasks from a particular garden
     * @param person The person who owns the tasks
     * @param garden The garden where the tasks are located
     * @return An ArrayList of all the user's tasks in the garden
     */
    ArrayList<Task> getUserTasksFromGarden(IPerson person, Garden garden);

    /**
     * Gets the date that a task is to be completed
     * @param task The task to get the completed date from
     * @return The expected completed date for that task
     */
    LocalDate getCompletedDate(Task task);

    /**
     * Sets the date a task is expected to be completed by
     * @param task The task to be completed
     * @param completedDate The date that the task needs to be completed by
     */
    void setCompletedDate(Task task, LocalDate completedDate);
}
