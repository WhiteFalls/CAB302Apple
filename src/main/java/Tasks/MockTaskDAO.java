package Tasks;

import People.Garden;
import People.IPerson;
import People.Person;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MockTaskDAO implements ITaskDAO{
    private static int autoIncrementedIdTask = 1;


    @Override
    public ArrayList<Task> getUserTasks(IPerson person) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);
        Task task = new Task(1, "Wash Beans", aDate, dDate, taskCategory.DAILY);
        task.setId(autoIncrementedIdTask);
        userTasks.add(task);

        autoIncrementedIdTask++;

        return userTasks;
    }

    @Override
    public void delete(Task task) {

    }

    public void update(Task oldTask, Task newTask)
    {

    }

    @Override
    public void add(Task task, IPerson person) {
        task.setId(autoIncrementedIdTask);
        autoIncrementedIdTask++;
    }

    @Override
    public ArrayList<Task> getCategorisedTasks(IPerson person, taskCategory category) {
        ArrayList<Task> categorisedTasks = new ArrayList<>();
        for (Task task : person.getTasks()){
            if(task.getCategory() == category) {
                categorisedTasks.add(task);
            }
        }
        return categorisedTasks;
    }

    @Override
    public ArrayList<Task> getCategorisedTasksFromGarden(IPerson person, taskCategory category, Garden garden) {
        return null;
    }

    @Override
    public Task get(int id) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Object getUserTasks(Person person) {
        return null;
    }


}
