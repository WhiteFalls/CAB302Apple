package Tasks;

import People.IPerson;
import People.Person;

import java.util.ArrayList;
import java.util.List;

public interface ITaskDAO {
    ArrayList<Task> getUserTasks(IPerson person);

    /**
     * Deletes a task from the database
     * @param task The task to be deleted
     */
    public void delete(Task task);

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
    public void add(Task task, IPerson person);

    /**
     * Returns all the tasks of a person associated with a certain category
     * @param person
     * @param category
     * @return
     */
    public ArrayList<Task> getCategorisedTasks(IPerson person, taskCategory category);

    Task get(int id);

    List<Task> getAllTasks();

    void update(Task task);

    void delete(int id);

    Object getUserTasks(Person person);

    List<Task> getCategorisedTasks(Person person, taskCategory taskCategory);
}
