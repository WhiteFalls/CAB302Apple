package People;

import Tasks.Task;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public interface IPerson {
    /**
     * Returns person's name
     * @return a string of the user's name
     */

    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getName();

    int getId();
    void setId(int userId);

    String getPassword();

    String getEmail();
    void setEmail(String email);

    List<Task> getTasks();
    void setTasks(ArrayList<Task> tasks);

    Task getTask(int id);

    void editTask(Task task);
    void addTask(Task task);


    void removeTask(int id);

}
