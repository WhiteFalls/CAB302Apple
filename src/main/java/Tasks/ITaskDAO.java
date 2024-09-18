package Tasks;

import People.Person;

import java.util.List;

public interface ITaskDAO {
    void add(Task task);
    Task get(int id);
    List<Task> getAllTasks();
    void update(Task task);
    void delete(int id);
    Object getUserTasks(Person person);
    List<Task> getCategorisedTasks(Person person, taskCategory taskCategory);
}
