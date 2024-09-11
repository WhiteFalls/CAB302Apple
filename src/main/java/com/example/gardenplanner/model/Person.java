package com.example.gardenplanner.model;


import java.util.ArrayList;

public class Person  implements IPerson {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int userId;
    private ArrayList<Task> tasks= new ArrayList<>();;

    public Person(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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
    public void removeTask(int id) {
        for (Task task : tasks)
        {
            if (task.getId() == id)
            {
                tasks.remove(task);
                break;
            }
        }
    }
}
