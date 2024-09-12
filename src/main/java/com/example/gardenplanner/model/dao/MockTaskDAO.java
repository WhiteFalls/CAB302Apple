package com.example.gardenplanner.model.dao;


import com.example.gardenplanner.model.person.IPerson;
import com.example.gardenplanner.model.task.Task;
import com.example.gardenplanner.model.task.taskCategories;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MockTaskDAO implements ITaskDAO {
    private static int autoIncrementedIdTask = 1;

    @Override
    public ArrayList<Task> getUserTasks(IPerson person) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);
        Task task = new Task("Wash Beans thoroughly!", aDate, dDate, taskCategories.DAILY);
        task.setId(autoIncrementedIdTask);
        userTasks.add(task);

        autoIncrementedIdTask++;

        return userTasks;
    }


    @Override
    public ArrayList<Task> getCategorisedTasks(IPerson person, String category) {
        ArrayList<Task> categorisedTasks = new ArrayList<>();
        for (Task task : person.getTasks()){
            if(task.getCategory() == taskCategories.valueOf(category.toUpperCase())) {
                categorisedTasks.add(task);
            }
        }
        return categorisedTasks;
    }

    @Override
    public void delete(int id) {

    }

    public void update(Task task)
    {

    }

    @Override
    public void add(Task task) {
        task.setId(autoIncrementedIdTask);
        autoIncrementedIdTask++;
    }

}


