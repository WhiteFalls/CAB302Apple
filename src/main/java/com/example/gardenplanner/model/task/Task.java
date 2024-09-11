package com.example.gardenplanner.model.task;


import com.example.gardenplanner.model.person.IPerson;

import java.time.LocalDate;

public class Task implements ITask {
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private int taskId;
    private Category category;

    public enum Category {
        DAILY,
        WEEKLY,
        CUSTOM
    }

    public Task(String taskDetails, LocalDate assignedDate, LocalDate dueDate, Category category)
    {
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.category = category;
    }

    public void setId(int taskId)
    {
        this.taskId = taskId;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    public boolean isCompleted() {
        return false;
    }

    public void assignTo(IPerson person) {

    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getId() {
        return taskId;
    }
}

