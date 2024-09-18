package Tasks;

import java.time.LocalDate;

public interface ITask {

    boolean isCompleted();

    void assignTo(IMockPerson person);

    /**
     * Returns the details of what needs to be done in the task
     * @return A string of the task details
     */
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
     * Returns the unique id of the task
     * @return An integer of the task's id
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
     * Updates the currents tasks parameters
     * @param newTask A task object that holds all the new parameters for the current task object
     */
    void updateTask(Task newTask);

}
