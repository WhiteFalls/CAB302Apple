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

    String getLastName();

    String getName();

    int getId();

    String getPassword();

    String getEmail();

    List<Task> getTasks();

}
