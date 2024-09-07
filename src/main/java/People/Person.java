package People;

import Tasks.Task;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class Person  implements  IPerson{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int userId;
    private ListView<Task> tasks;

    public Person(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void setId(int userId) {
        this.userId = userId;
    }

    public void setTasks(ListView<Task> tasks)
    {
        this.tasks = tasks;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getName() {
        return firstName.concat(lastName);
    }

    @Override
    public int getId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public ListView<Task> getTasks() {
        return tasks;
    }
}
