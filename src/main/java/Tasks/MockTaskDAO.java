package Tasks;

import People.IPerson;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MockTaskDAO implements ITaskDAO{
    private static int autoIncrementedId = 0;

    @Override
    public ArrayList<Task> getUserTasks(IPerson person) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);
        Task task = new Task("Wash Beans", aDate, dDate);
        task.setId(autoIncrementedId);
        userTasks.add(task);

        autoIncrementedId++;

        return userTasks;
    }

    @Override
    public void delete(int id) {

    }

    public void update(Task task)
    {

    }

    @Override
    public void add(Task task) {

    }

}
