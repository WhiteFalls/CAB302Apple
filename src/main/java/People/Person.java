//This File was and still is pretty mad
//

package People;

import Tasks.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlSourceToSinkFlow")
public class Person implements IPerson {

    private int userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<Task> tasks = new ArrayList<>();
    private String ivBase64;

    // Primary Constructor
    public Person(int userId, String firstName, String lastName, String email, String password, String ivBase64) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.ivBase64 = ivBase64;
    }

    // Constructor for loading a Person from the database using userId
    public Person(int userId, Connection connection) {
        this.userId = userId;
        loadPersonFromDatabase(connection);  // Load person details from the database
    }

    // Constructor for creating a new Person (no userId yet)
    public Person(String firstName, String lastName, String email, String password, String ivBase64) {
        this(0, firstName, lastName, email, password, ivBase64);  // Calls the primary constructor with userId = 0
    }

    // Loads the person's details from the database
    private void loadPersonFromDatabase(Connection connection) {
        String query = "SELECT fname, lname, email, password, iv_base64 FROM Users WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.firstName = resultSet.getString("fname");
                this.lastName = resultSet.getString("lname");
                this.email = resultSet.getString("email");
                this.password = resultSet.getString("password");
                this.ivBase64 = resultSet.getString("iv_base64");
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
        return firstName + " " + lastName;
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
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIvBase64() {
        return ivBase64;
    }

    public void setIvBase64(String ivBase64) {
        this.ivBase64 = ivBase64;
    }

    @Override
    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }

    @Override
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void editTask(Task newTask, Task oldTask) {
        int index = tasks.indexOf(oldTask);
        if (index >= 0) {
            tasks.set(index, newTask);
        }
    }

    @Override
    public void removeTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    @Override
    public Task getNewestTask() {
        return tasks.isEmpty() ? null : tasks.getLast();  // UnFixing the .getLast() error
    }
}
