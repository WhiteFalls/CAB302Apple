package com.example.gardenplanner.model.task;


import com.example.gardenplanner.model.person.IPerson;

import java.time.LocalDate;

public interface ITask {

    boolean isCompleted();

    void assignTo(IPerson person);

    String getTaskDetails();

    LocalDate getAssignedDate();

    LocalDate getDueDate();

    int getId();

    void setId(int taskId);

    Task.Category getCategory();
}
