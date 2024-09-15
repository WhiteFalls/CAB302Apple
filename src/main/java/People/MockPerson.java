package People;

import People.IMockPerson;
import Tasks.Task;

import java.util.ArrayList;

public class MockPerson implements IMockPerson {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int userId;
    private ArrayList<Task> tasks;

    /**
     * Contructs a new person with the speified first name, last name, email and password.
     * Also, initialises the list of the users tasks
     * @param firstName The first name of the person
     * @param lastName The last name of the person
     * @param email The person's email
     * @param password The person's password for logging into their account
     */
    public MockPerson(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.tasks = new ArrayList<>();
    }

    public void setId(int userId) {
        this.userId = userId;
    }

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
    public void setPassword(String password) {

    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int id) {
        for (Task task : tasks)
        {
            if (task.getId() == id)
            {
                return task;
            }
        }
        return null;
    }

    public Task getNewestTask()
    {
        return tasks.getLast();
    }

    @Override
    public void editTask(Task newTask, Task oldTask) {
        tasks.remove(oldTask);
        tasks.add(newTask);
    }

    public void addTask(Task task)
    {
        tasks.add(task);
    }

    @Override
    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
