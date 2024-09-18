//This File was and still is pretty mad

package People;

import Tasks.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Person implements IPerson {
    private static final Logger LOGGER = Logger.getLogger(Person.class.getName());

    private int userId;  // User ID should be an integer not a string XD
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<Task> tasks = new ArrayList<>();

    // Constructor for loading from database
    public Person(int userId, Connection connection) {
        this.userId = userId;
        loadPersonFromDatabase(connection);
    }

    // Constructor for new Person object to be inserted
    public Person(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Person(int userId, String fname, String lname, String email, String password) {
    }

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
    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }

    @Override
    public void setTasks(Object userTasks) {

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
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {

    }

    @Override
    public void setUserId(int userId) {
        throw new UnsupportedOperationException("User ID is immutable.");
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
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    private void loadPersonFromDatabase(Connection connection) {
        String query = "SELECT fname, lname, email, password FROM Users WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.firstName = resultSet.getString("fname");
                this.lastName = resultSet.getString("lname");
                this.email = resultSet.getString("email");
                this.password = resultSet.getString("password");
            } else {
                LOGGER.warning("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load person from database", e);
        }
    }

    public void updatePersonInDatabase(String field, String value, Connection connection) {
        String updateQuery = "UPDATE Users SET " + field + " = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                LOGGER.info("User " + field + " updated successfully.");
            } else {
                LOGGER.warning("Failed to update " + field + " for user " + userId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update person in database", e);
        }
    }

    public void updateDetailsInDatabase(Connection connection) {
        if (firstName != null && !firstName.isEmpty()) {
            updatePersonInDatabase("fname", firstName, connection);
        }
        if (lastName != null && !lastName.isEmpty()) {
            updatePersonInDatabase("lname", lastName, connection);
        }
        if (email != null && !email.isEmpty()) {
            updatePersonInDatabase("email", email, connection);
        }
        if (password != null && !password.isEmpty()) {
            updatePersonInDatabase("password", password, connection);
        }
    }
}
