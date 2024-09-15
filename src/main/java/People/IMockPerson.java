package People;

import Tasks.Task;

import java.util.ArrayList;

public interface IMockPerson {
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
     * @return a String of the user's ID.
     */
    int getId();

    /**
     * Sets the user's unique ID.
     * @param userId A String containing the user's unique ID.
     */
    void setId(int userId);

    /**
     * Returns the full name of the user (combines first and last name).
     * @return a String representing the user's full name.
     */
    default String getName() {
        return getFirstName() + " " + getLastName();
    }

    /**
     * Returns all the person's tasks.
     * @return An ArrayList of the persons tasks
     */
    ArrayList<Task> getTasks();

    /**
     * Sets the user's tasks
     * @param tasks The tasks associated with the user
     */
    void setTasks(ArrayList<Task> tasks);

    /**
     * Returns a task with the same task id
     * @param id The task id of the task wanting to be returned
     * @return The task with the matching task id
     */
    Task getTask(int id);

    /**
     *
     * @return
     */
    public Task getNewestTask();


    /**
     * Replaces an old task with a new task
     * @param newTask The new task to replace the old task
     * @param oldTask THe old task to be replaced
     */
    void editTask(Task newTask, Task oldTask);

    /**
     * Adds a new task to the user
     * @param task The task to be added
     */
    void addTask(Task task);


    /**
     * Removes a task from the user
     * @param task The task to be removed
     */
    void removeTask(Task task);



}
