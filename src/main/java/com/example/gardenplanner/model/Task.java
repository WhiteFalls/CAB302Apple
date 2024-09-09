package com.example.gardenplanner.model;

public class Task implements ITask {
    private final String taskName;
    private String userId;
    private final String gardenId;
    private Boolean isCompleted;
    private int taskId;
    private final int timeFrame;

    public Task(String taskName, String userId, String gardenId, boolean isCompleted, int timeFrame) {
        this.taskName = taskName;
        this.userId = userId;
        this.gardenId = gardenId;
        this.isCompleted = isCompleted;
        this.timeFrame = timeFrame;
    }
    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public void assignTo(IPerson person) {
        userId = person.getUserId();
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    public String getUserId(){return userId;}
    public String getGardenId(){return gardenId;}
    public void updateCompletion(){isCompleted = true;}
    public int getTaskId() {return taskId;}
    public void setTaskId(int taskId){this.taskId = taskId;}
    public int getTimeFrame() {return timeFrame;}
}
