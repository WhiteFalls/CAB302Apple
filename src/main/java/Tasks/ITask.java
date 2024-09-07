package Tasks;

import People.IPerson;

import java.time.LocalDate;

public interface ITask {

    boolean isCompleted();

    void assignTo(IPerson person);

    String getTaskDetails();

    LocalDate getAssignedDate();

    LocalDate getDueDate();

}
