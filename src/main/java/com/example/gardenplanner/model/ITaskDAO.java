package com.example.gardenplanner.model;


import java.util.ArrayList;

public interface ITaskDAO {
    ArrayList<Task> getUserTasks(IPerson person);
    ArrayList<Task> getCategorisedTasks(IPerson person, String category);

    public void delete(int id);

    public void update(Task task);

    public void add(Task task);
}
