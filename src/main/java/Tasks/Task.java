package Tasks;
import People.IPerson;

import java.time.LocalDate;
import java.util.Objects;


public class Task implements ITask {
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private int taskId;
    private taskCategory category;

    /**
     * Contructs a new task with the specified ID, task details, assigned date, due date and category type
     *
     * @param taskId
     * @param taskDetails  The details of the task that needs to be completed
     * @param assignedDate the assigned date of the task
     * @param dueDate      the due date of the task
     * @param category     the category of how often the task needs to be completed
     */
    public Task(int taskId, String taskDetails, LocalDate assignedDate, LocalDate dueDate, taskCategory category)
    {
        this.taskId = taskId;
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.category = category;
    }

    public Task(String taskDetails, LocalDate assignedDate, LocalDate dueDate, taskCategory category)
    {
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.category = category;
    }

    public void setId(int taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getId() {
        return taskId;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public taskCategory getCategory() {
        return category;
    }

    public void setCategory(taskCategory category) {
        this.category = category;
    }

    /**
     * Updates the task
     * @param newTask A task object that holds all the new parameters for the current task object
     */
    public void updateTask(Task newTask)
    {
        this.taskDetails = newTask.taskDetails;
        this.assignedDate = newTask.assignedDate;
        this.dueDate = newTask.dueDate;
        this.category = newTask.category;
    }

}
