package Tasks;

import People.IMockPerson;

import java.util.ArrayList;

public interface ITaskDAO {
    ArrayList<Task> getUserTasks(IMockPerson person);

    /**
     * Deletes a task from the database
     * @param task The task to be deleted
     */
    public void delete(Task task);

    /**
     * Updates a task in the database
     * @param task The task to be updated
     */
    public void update(Task oldTask, Task newTask);

    /**
     * Add a task to the database
     * @param task The task to be added
     */
    public void add(Task task);

    /**
     * Returns all the tasks of a person associated with a certain category
     * @param person
     * @param category
     * @return
     */
    public ArrayList<Task> getCategorisedTasks(IMockPerson person, taskCategory category);
}
