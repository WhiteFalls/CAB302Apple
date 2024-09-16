package Tasks;

import People.IMockPerson;

import java.time.LocalDate;

public interface ITask {

    boolean isCompleted();

    void assignTo(IMockPerson person);

    String getTaskDetails();

    LocalDate getAssignedDate();

    LocalDate getDueDate();

    int getId();

    void setId(int taskId);

    taskCategory getCategory();

}
