package Database;

import GardenCell.Garden;
import People.IPerson;
import People.Person;
import Tasks.Task;
import Tasks.taskCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ITaskDAO {
    ArrayList<Task> getUserTasks(IPerson person);

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
     * Returns all the tasks of a person associated with a certain category
     * @param person
     * @param category
     * @return
     */
    ArrayList<Task> getCategorisedTasks(IPerson person, taskCategory category);

    ArrayList<Task> getCategorisedTasksFromGarden(IPerson person, taskCategory category, Garden garden);

    Task get(int id);

    List<Task> getAllTasks();

    void update(Task task);

    void delete(int id);

    Object getUserTasks(Person person);

    ArrayList<Task> getUserTasksFromGarden(IPerson person, Garden garden);

    LocalDate getCompletedDate(Task task);

    void setCompletedDate(Task task, LocalDate completedDate);
}