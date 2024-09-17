package Tasks;

import java.time.LocalDate;

public interface ITask {
    int getId();
    String getTaskDetails();
    LocalDate getAssignedDate();
    LocalDate getDueDate();
    taskCategory getCategory();
}
