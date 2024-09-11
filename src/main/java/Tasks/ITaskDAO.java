package Tasks;

import People.IPerson;

import java.util.ArrayList;

public interface ITaskDAO {
    ArrayList<Task> getUserTasks(IPerson person);

    public void delete(int id);

    public void update(Task task);

    public void add(Task task);
}
