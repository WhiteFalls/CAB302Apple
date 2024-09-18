package Tasks;

import java.time.LocalDate;

public class Task {
    private int id;  // Unique ID from the database
    private String taskDetails;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private taskCategory category;

    // Constructor for creating new tasks (without an ID yet (lack of skill))
    public Task(String taskDetails, LocalDate assignedDate, LocalDate dueDate, taskCategory category) {
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.category = category;
    }

    // Constructor for tasks loaded from the database
    public Task(int id, String taskDetails, LocalDate assignedDate, LocalDate dueDate, taskCategory category) {
        this.id = id;
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.category = category;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public taskCategory getCategory() {
        return category;
    }

    public void setCategory(taskCategory category) {
        this.category = category;
    }
}
