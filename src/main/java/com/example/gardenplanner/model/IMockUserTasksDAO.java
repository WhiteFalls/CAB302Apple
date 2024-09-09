package com.example.gardenplanner.model;

import java.util.List;

public interface IMockUserTasksDAO {
    /**
     * Adds a new task to the database.
     * @param task The task to add.
     */
    public void addTask(Task task);
    /**
     * Updates an existing task in the database.
     * @param task The task to update.
     */
    public void updateTask(Task task);
    /**
     * Deletes a task from the database.
     * @param task The task to delete.
     */
    public void deleteTask(Task task);
    /**
     * Retrieves a task from the database.
     * @param id The id of the contact to retrieve.
     * @return The task with the given id, or null if not found.
     */
    public Task getTask(int id);
    /**
     * Retrieves all task from the database.
     * @return A list of all task in the database.
     */
    public List<Task> getAllTasks();
}
