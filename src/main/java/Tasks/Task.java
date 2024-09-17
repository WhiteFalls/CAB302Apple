package Tasks;
import java.time.LocalDate;

public class Task implements ITask {
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private int taskId;
    private taskCategory category;

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

    public boolean isCompleted() {
        return false;
    }

    public void assignTo(IMockPerson person) {

    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getId() {
        return taskId;
    }

    @Override
    public taskCategory getCategory() {
        return category;
    }
}

