package Tasks;

import java.time.LocalDate;

public interface ITask {
    String getTaskDetails();

    /**
     * Returns the date that the task was assigned to the user
     * @return A LocalDate formatted version of the task's assigned date
     */
    LocalDate getAssignedDate();

    /**
     * Returns the date that the task is due to be completed
     * @return A LocalDate formatted version of the task's due date
     */
    LocalDate getDueDate();

    /**
     * Returns the unique ID of the task
     * @return An integer of the task's ID
     */
    int getId();

    /**
     * Sets the task's unique id
     * @param taskId The value of the task's id
     */
    void setId(int taskId);

    /**
     * Returns the category of the task that details how often it must be completed
     * @return A taskCategory enum of the task's repetition category
     */
    taskCategory getCategory();

    /**
     * Sets the category of the task
     * @param category The category of the task
     */
    public void setCategory(taskCategory category);


    /**
     * Updates the currents tasks parameters
     * @param newTask A task object that holds all the new parameters for the current task object
     */
    void updateTask(Task newTask);

}
