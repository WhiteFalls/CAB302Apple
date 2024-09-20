package People;

import Tasks.Task;

import java.util.List;

public interface IPerson {
    /**
     * Returns the user's first name.
     * @return a String of the user's first name.
     */
    String getFirstName();

    /**
     * Sets the user's first name.
     * @param firstName A String containing the user's first name.
     */
    void setFirstName(String firstName);

    /**
     * Returns the user's last name.
     * @return a String of the user's last name.
     */
    String getLastName();

    /**
     * Sets the user's last name.
     * @param lastName A String containing the user's last name.
     */
    void setLastName(String lastName);

    /**
     * Returns the user's email.
     * @return a String of the user's email.
     */
    String getEmail();

    /**
     * Sets the user's email.
     * @param email A String containing the user's email.
     */
    void setEmail(String email);

    void setUserId(int userId);

    /**
     * Returns the user's password.
     * @return a String of the user's password.
     */
    String getPassword();

    /**
     * Sets the user's password.
     * @param password A String containing the user's password.
     */
    void setPassword(String password);

    /**
     * Returns the user's unique ID.
     *
     * @return a String of the user's ID.
     */
    int getUserId();

    /**
     * Sets the user's unique ID.
     * @param userId A String containing the user's unique ID.
     */
    void setUserId(String userId);

    /**
     * Returns the full name of the user (combines first and last name).
     * @return a String representing the user's full name.
     */
    default String getName() {
        return getFirstName() + " " + getLastName();
    }

    /**
     * Gets all the tasks assigned to the user
     * @return An array of all the user's tasks
     */
    Task[] getTasks();

    /**
     * Gets the newest task of the user
     * @return The most recent task assigned to the user
     */
    Task getNewestTask();

    /**
     * Sets the tasks assigned to the user
     * @param userTasks The tasks to be assigned to the user
     */
    void setTasks(Object userTasks);

    /**
     * Sets the tasks assigned to the user
     * @param tasks The tasks to be assigned to the user
     */
    void setTasks(List<Task> tasks);

    /**
     * Adds a task to the user
     * @param task The task to be added
     */
    void addTask(Task task);

    /**
     * Updates a task currently assigned to the user
     * @param newTask The task to replace the old task
     * @param oldTask The task to be replaced
     */
    void editTask(Task newTask, Task oldTask);

    /**
     * Removes a task from the user
     * @param id The ID of the task to be removed
     */
    void removeTask(int id);
}
