package Tasks;

import People.IMockPerson;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MockTaskDAO implements ITaskDAO{
    private static int autoIncrementedIdTask = 1;


    @Override
    public ArrayList<Task> getUserTasks(IMockPerson person) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);
        Task task = new Task("Wash Beans", aDate, dDate, taskCategory.DAILY);
        task.setId(autoIncrementedIdTask);
        userTasks.add(task);

        autoIncrementedIdTask++;

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
        task.setId(autoIncrementedIdTask);
        autoIncrementedIdTask++;
    }

    @Override
    public ArrayList<Task> getCategorisedTasks(IMockPerson person, taskCategory category) {
        ArrayList<Task> categorisedTasks = new ArrayList<>();
        for (Task task : person.getTasks()){
            if(task.getCategory() == category) {
                categorisedTasks.add(task);
            }
        }
        return categorisedTasks;
    }

}