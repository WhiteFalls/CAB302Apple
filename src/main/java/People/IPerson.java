package People;

import Tasks.Task;
import java.util.List;

public interface IPerson {

    // Getters and Setters for Basic Information

    /**
     * Returns the user's first name.
     * @return the user's first name.
     */
    String getFirstName();

    /**
     * Sets the user's first name.
     * @param firstName the user's first name.
     */
    void setFirstName(String firstName);

    /**
     * Returns the user's last name.
     * @return the user's last name.
     */
    String getLastName();

    /**
     * Sets the user's last name.
     * @param lastName the user's last name.
     */
    void setLastName(String lastName);

    /**
     * Returns the user's email.
     * @return the user's email.
     */
    String getEmail();

    /**
     * Sets the user's email.
     * @param email the user's email.
     */
    void setEmail(String email);

    /**
     * Returns the user's password (encrypted).
     * @return the user's encrypted password.
     */
    String getPassword();

    /**
     * Sets the user's password (encrypted).
     * @param password the user's encrypted password.
     */
    void setPassword(String password);

    /**
     * Returns the user's unique ID.
     * @return the user's ID.
     */
    int getUserId();

    /**
     * Sets the user's unique ID.
     * @param userId the user's unique ID.
     */
    void setUserId(int userId);

    /**
     * Gets the IV (Initialization Vector) used for password encryption.
     * @return the IV in Base64 format.
     */
    String getIvBase64();

    /**
     * Sets the IV (Initialization Vector) for password encryption.
     * @param ivBase64 the IV in Base64 format.
     */
    void setIvBase64(String ivBase64);

    // Task Management

    /**
     * Returns an array of tasks assigned to the user.
     * @return an array of tasks.
     */
    Task[] getTasks();

    /**
     * Sets the tasks assigned to the user.
     * @param tasks the list of tasks assigned to the user.
     */
    void setTasks(List<Task> tasks);

    /**
     * Adds a task to the user's task list.
     * @param task the task to be added.
     */
    void addTask(Task task);

    /**
     * Edits a task in the user's task list.
     * @param newTask the new task to replace the old one.
     * @param oldTask the old task to be replaced.
     */
    void editTask(Task newTask, Task oldTask);

    /**
     * Removes a task from the user's task list by its ID.
     * @param id the ID of the task to be removed.
     */
    void removeTask(int id);

    /**
     * Gets the newest (most recent) task assigned to the user.
     * @return the most recent task, or null if no tasks exist.
     */
    Task getNewestTask();

    /**
     * Returns the full name of the user by combining their first name and last name.
     * @return a String representing the user's full name, in the format "FirstName LastName".
     */
    String getName();
}
