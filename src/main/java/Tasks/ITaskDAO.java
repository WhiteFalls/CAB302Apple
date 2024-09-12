package Tasks;

import People.IMockPerson;

import java.util.ArrayList;

public interface ITaskDAO {
    ArrayList<Task> getUserTasks(IMockPerson person);

    public void delete(int id);

    public void update(Task task);

    public void add(Task task);

    public ArrayList<Task> getCategorisedTasks(IMockPerson person, taskCategory category);
}
