package Tasks;
import People.IPerson;
import java.time.LocalDate;

public class Task implements ITask {
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private int taskId;

    /**
     * Contructs a new task with the specified task details, assigned date, due date and category type
     * @param taskDetails
     * @param assignedDate
     * @param dueDate
     */
    public Task(String taskDetails, LocalDate assignedDate, LocalDate dueDate)
    {
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getId() {
        return taskId;
    }
}

