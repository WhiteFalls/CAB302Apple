<<<<<<<< HEAD:src/main/java/Tasks/MockTaskDAO.java
package Tasks;
========
package com.example.gardenplanner.model.dao;
>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/dao/MockTaskDAO.java

import People.IMockPerson;

import com.example.gardenplanner.model.person.IPerson;
import com.example.gardenplanner.model.task.Task;
import com.example.gardenplanner.model.task.taskCategories;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MockTaskDAO implements ITaskDAO{
    private static int autoIncrementedIdTask = 1;


    @Override
    public ArrayList<Task> getUserTasks(IMockPerson person) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        LocalDate aDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate dDate = LocalDate.of(2024, Month.APRIL, 1);
<<<<<<<< HEAD:src/main/java/Tasks/MockTaskDAO.java
        Task task = new Task("Wash Beans", aDate, dDate, taskCategory.DAILY);
========
        Task task = new Task("Wash Beans thoroughly!", aDate, dDate, taskCategories.DAILY);
>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/dao/MockTaskDAO.java
        task.setId(autoIncrementedIdTask);
        userTasks.add(task);

        autoIncrementedIdTask++;

        return userTasks;
    }

<<<<<<<< HEAD:src/main/java/Tasks/MockTaskDAO.java
========

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

>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/dao/MockTaskDAO.java
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

    @Override
    public ArrayList<Task> getCategorisedTasks(IMockPerson person, taskCategory category) {
        ArrayList<Task> categorisedTasks = new ArrayList<>();
        for (Task task : person.getTasks()){
            if(task.getCategory() == category) {
                categorisedTasks.add(task);
            }
        }
        return categorisedTasks;
    }

}
