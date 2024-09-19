package Tasks;
import People.IPerson;

import java.time.LocalDate;


public class Task implements ITask {
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private int taskId;
    private taskCategory category;

    /**
     * Contructs a new task with the specified task details, assigned date, due date and category type
     *
     * @param taskId
     * @param taskDetails  The details of the task that needs to be completed
     * @param assignedDate the assigned date of the task
     * @param dueDate      the due date of the task
     * @param category     the category of how often the task needs to be completed
     */
    public Task(int taskId, String taskDetails, LocalDate assignedDate, LocalDate dueDate, taskCategory category)
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

    public boolean isCompleted() {
        return false;
    }

    public void assignTo(IPerson person) {

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

    public void updateTask(Task newTask)
    {
        this.taskDetails = newTask.taskDetails;
        this.assignedDate = newTask.assignedDate;
        this.dueDate = newTask.dueDate;
        this.category = newTask.category;
    }
}
