<<<<<<<< HEAD:src/main/java/Tasks/ITask.java
package Tasks;
========
package com.example.gardenplanner.model.task;
>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/task/ITask.java

import People.IMockPerson;

import com.example.gardenplanner.model.person.IPerson;

import java.time.LocalDate;

public interface ITask {



    boolean isCompleted();

    void assignTo(IMockPerson person);

    String getTaskDetails();

    LocalDate getAssignedDate();

    LocalDate getDueDate();

    int getId();

    void setId(int taskId);

<<<<<<<< HEAD:src/main/java/Tasks/ITask.java
    taskCategory getCategory();

========
    taskCategories getCategory();
>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/task/ITask.java
}
