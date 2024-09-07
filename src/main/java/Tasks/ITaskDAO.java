package Tasks;

import People.IPerson;

import java.util.ArrayList;

public interface ITaskDAO {
    ArrayList<Task> getUserTasks(IPerson person);
}
