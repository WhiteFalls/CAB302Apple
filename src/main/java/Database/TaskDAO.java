package Database;

import GardenCell.Garden;
import People.IPerson;
import Tasks.Task;
import Tasks.taskCategory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskDAO implements ITaskDAO {

    private Connection connection;

    public TaskDAO() {
        connection = DatabaseConnection.getConnection();  // Get the connection from your utility
    }


    /**
     * Deletes task from the database
     * @param task The task to be deleted
     */
    @Override
    public void delete(Task task) {
        String query = "DELETE FROM Tasks WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes tasks from the user and garden
     * @param person The user for tasks to be deleted from
     * @param garden The garden for tasks to be deleted from
     */
    @Override
    public void deleteUserTasks(IPerson person, Garden garden) {
        String query = "DELETE FROM Tasks WHERE user_id = ? AND garden_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person.getUserId());
            stmt.setInt(2, garden.getGardenId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the tasks
     * @param oldTask The task to be updated
     * @param newTask The task that will replace oldTask
     */
    @Override
    public void update(Task oldTask, Task newTask) {
        String query = "UPDATE Tasks SET task_details = ?, assigned_date = ?, due_date = ?, category = ? WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newTask.getTaskDetails());
            stmt.setDate(2, Date.valueOf(newTask.getAssignedDate()));
            stmt.setDate(3, Date.valueOf(newTask.getDueDate()));
            stmt.setString(4, newTask.getCategory().toString());  // Update category as text
            stmt.setInt(5, oldTask.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the task into the database
     * @param task The task to be added
     * @param person The person to add the task to
     * @param garden The garden the task is assigned in
     */
    @Override
    public void add(Task task, IPerson person,Garden garden) {
        String query = "INSERT INTO Tasks (user_id, garden_id, task_details, assigned_date, due_date, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, person.getUserId());
            stmt.setInt(2, garden.getGardenId()); // was hardcoded to be 1
            stmt.setString(3, task.getTaskDetails());
            stmt.setDate(4, Date.valueOf(task.getAssignedDate()));
            stmt.setDate(5, Date.valueOf(task.getDueDate()));
            stmt.setString(6, task.getCategory().toString());  // Store category as text
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getInt(1));  // Set the generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves tasks based on its category (DAILY,WEEKLY,CUSTOM) and garden
     * @param person The person who owns the tasks
     * @param category The category to group the tasks
     * @param garden The garden that the tasks belong to
     * @return An array list of the tasks based on its category and garden
     */
    @Override
    public ArrayList<Task> getCategorisedTasksFromGarden(IPerson person, taskCategory category, Garden garden) {
        String query = "SELECT * FROM Tasks WHERE user_id = ? AND category = ? AND garden_id = ?";
        ArrayList<Task> tasks = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person.getUserId());
            stmt.setString(2, category.toString());
            stmt.setInt(3, garden.getGardenId());
            ResultSet rs = stmt.executeQuery();
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

    /**
     * Updates the task
     * @param task The tasks containing the new information and the same ID as the task to be replaced
     */
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

    /**
     * Deletes task given its task ID
     * @param id The ID of the task to be deleted
     */
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

    /**
     * Retrieves all tasks from the user in the garden
     * @param person The person who owns the tasks
     * @param garden The garden where the tasks are located
     * @return An array list of tasks based on the user ID and garden ID
     */
    @Override
    public ArrayList<Task> getUserTasksFromGarden(IPerson person, Garden garden) {
        String query = "SELECT * FROM Tasks WHERE user_id = ? AND garden_id = ?";
        ArrayList<Task> tasks = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person.getUserId());
            stmt.setInt(2, garden.getGardenId());
            ResultSet rs = stmt.executeQuery();
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

    /**
     * Retrieves the task's completed date
     * @param task The task to get the completed date from
     * @return The date at which the task was completed
     */
    @Override
    public LocalDate getCompletedDate(Task task){
        String query = "SELECT completed_date FROM Tasks WHERE task_id = ?";
        LocalDate completedDate = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, task.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date date = rs.getDate(1);
                if (date != null){
                    completedDate = date.toLocalDate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return completedDate;
    }

    /**
     * Sets the task's completed date
     * @param task The task to be completed
     * @param completedDate The date that the task needs to be completed by
     */
    @Override
    public void setCompletedDate(Task task, LocalDate completedDate){
        String query = "UPDATE Tasks SET completed_date = ? WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (completedDate != null) stmt.setDate(1, Date.valueOf(completedDate));
            else stmt.setDate(1,null);
            stmt.setInt(2, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
