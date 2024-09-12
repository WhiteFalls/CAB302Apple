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
    void setUserId(String userId);

    /**
     * Returns the full name of the user (combines first and last name).
     * @return a String representing the user's full name.
     */
    default String getName() {
        return getFirstName() + " " + getLastName();
    }

    ArrayList<Task> getTasks();
    void setTasks(ArrayList<Task> tasks);

    Task getTask(int id);

    void editTask(Task newTask, Task oldTask);

    void addTask(Task task);


    void removeTask(int id);



}
