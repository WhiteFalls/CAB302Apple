package com.example.gardenplanner.model;

import java.util.ArrayList;
import java.util.List;

public class MockUserTasksDAO implements ITask, IMockUserTasksDAO {
    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public void assignTo(IPerson person) {

    }

    @Override
    public String getTaskName() {
        return "";
    }

    /**
     * A static list of contacts to be used as a mock database.
     */
   // public static final ArrayList<Person> users = new ArrayList<>();
    private static int autoIncrementedId = 0;
    public static final ArrayList<Task> tasks = new ArrayList<>();

    public MockUserTasksDAO() {
        // Add some initial contacts to the mock database
        addTask(new Task("Clean", "1", "johndoe@example.com", false,1));
        addTask(new Task("Water", "2", "janedoe@example.com", false,2));
        addTask(new Task("Trim", "3", "jaydoe@example.com", false,3));
        addTask(new Task("Collect", "4", "jerrydoe@example.com",false,4));
    }

    public void addTask(Task task) {
        task.setTaskId(autoIncrementedId);
        autoIncrementedId++;
        tasks.add(task);
    }


    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId() == task.getTaskId()) {
                tasks.set(i, task);
                break;
            }
        }
    }


    public void deleteTask(Task task) {
        tasks.remove(task);
    }


    public Task getTask(int id) {
        for (Task task : tasks) {
            if (task.getTaskId() == id) {
                return task;
            }
        }
        return null;
    }


    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }


}
