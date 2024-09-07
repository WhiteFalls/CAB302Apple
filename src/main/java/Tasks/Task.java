package Tasks;
import People.IPerson;
import java.time.LocalDate;

public abstract class Task implements ITask {
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;

    public Task(String taskDetails, LocalDate assignedDate, LocalDate dueDate)
    {
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
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

    public LocalDate getDueDate() {
        return dueDate;
    }
}

