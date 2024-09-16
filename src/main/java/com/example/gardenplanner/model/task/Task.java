<<<<<<<< HEAD:src/main/java/Tasks/Task.java
package Tasks;
import People.IMockPerson;
========
package com.example.gardenplanner.model.task;


import com.example.gardenplanner.model.person.IPerson;

>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/task/Task.java
import java.time.LocalDate;


public class Task implements ITask {
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private int taskId;
<<<<<<<< HEAD:src/main/java/Tasks/Task.java
    private taskCategory category;

    public Task(String taskDetails, LocalDate assignedDate, LocalDate dueDate, taskCategory category)
========
    private taskCategories category;



    public Task(String taskDetails, LocalDate assignedDate, LocalDate dueDate, taskCategories category)
>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/task/Task.java
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

<<<<<<<< HEAD:src/main/java/Tasks/Task.java
========
    @Override
    public taskCategories getCategory() {
        return category;
    }

>>>>>>>> usertodoList:src/main/java/com/example/gardenplanner/model/task/Task.java
    public boolean isCompleted() {
        return false;
    }

    public void assignTo(IMockPerson person) {

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

    @Override
    public taskCategory getCategory() {
        return category;
    }
}

