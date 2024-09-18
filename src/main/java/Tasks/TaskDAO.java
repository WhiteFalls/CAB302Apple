package Tasks;

import Database.DatabaseConnection;
import People.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO {

    private Connection connection;

    public TaskDAO() {
        connection = DatabaseConnection.getConnection();  // Get the connection from your utility
    }

    @Override
    public void add(Task task) {
        String query = "INSERT INTO Tasks (task_details, assigned_date, due_date, category) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTaskDetails());
            stmt.setDate(2, Date.valueOf(task.getAssignedDate()));
            stmt.setDate(3, Date.valueOf(task.getDueDate()));
            stmt.setString(4, task.getCategory().toString());  // Store category as text
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getInt(1));  // Set the generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task get(int id) {
        String query = "SELECT * FROM Tasks WHERE task_id = ?";
        Task task = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                task = new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_details"),
                        rs.getDate("assigned_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        taskCategory.valueOf(rs.getString("category"))  // Convert text to enum
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        String query = "SELECT * FROM Tasks";
        List<Task> tasks = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_details"),
                        rs.getDate("assigned_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        taskCategory.valueOf(rs.getString("category"))  // Convert text to enum
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void update(Task task) {
        String query = "UPDATE Tasks SET task_details = ?, assigned_date = ?, due_date = ?, category = ? WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, task.getTaskDetails());
            stmt.setDate(2, Date.valueOf(task.getAssignedDate()));
            stmt.setDate(3, Date.valueOf(task.getDueDate()));
            stmt.setString(4, task.getCategory().toString());  // Update category as text
            stmt.setInt(5, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Tasks WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getUserTasks(Person person) {
        return null;
    }

    @Override
    public List<Task> getCategorisedTasks(Person person, taskCategory taskCategory) {
        return List.of();
    }
}
