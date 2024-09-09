package com.example.gardenplanner;

public class Task implements ITask {
    private String taskName;

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
}
