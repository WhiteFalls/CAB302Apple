package com.example.gardenplanner.controller;

import Tasks.ITaskDAO;
import Tasks.Task;
import Tasks.TaskDAO;
import Tasks.taskCategory;
import Database.IPersonDAO;
import Database.PersonDAO;
import People.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class GardenToDoListController {
    @FXML
    private Accordion dropboxTasks;
    @FXML
    private TitledPane weeklyTasks;
    @FXML
    private TitledPane dailyTasks;
    @FXML
    private TitledPane customTasks;

    private Connection connection;

    private ITaskDAO taskDAO;
    private IPersonDAO personDAO;
    private Person person;

    private ListView<Task> dailyListView = new ListView<>();
    private ListView<Task> weeklyListView = new ListView<>();
    private ListView<Task> customListView = new ListView<>();

    // Constructor
    public GardenToDoListController() {
        taskDAO = new TaskDAO();  // Use real TaskDAO
        personDAO = new PersonDAO(connection);  // Use real PersonDAO
        person = personDAO.getPerson(1);  // Load person from the database, person with id 1
        addTask(person, "Plant Beans", taskCategory.WEEKLY);
    }


    // Methods

    @FXML
    private void testAddTask(ActionEvent event) {
        addTask(person, "Harvest Beans", taskCategory.DAILY);
        addTask(person, "Wash Beans", taskCategory.WEEKLY);
        addTask(person, "Boil Beans", taskCategory.WEEKLY);
        addTask(person, "DESTROY Beans", taskCategory.CUSTOM);
        syncPerson(person);
    }

    private void syncPerson(Person person) {
        dailyListView.getItems().clear();
        weeklyListView.getItems().clear();
        customListView.getItems().clear();

        List<Task> dailyTasks = taskDAO.getCategorisedTasks(person, taskCategory.DAILY);
        List<Task> weeklyTasks = taskDAO.getCategorisedTasks(person, taskCategory.WEEKLY);
        List<Task> customTasks = taskDAO.getCategorisedTasks(person, taskCategory.CUSTOM);

        dailyListView.getItems().addAll(dailyTasks);
        weeklyListView.getItems().addAll(weeklyTasks);
        customListView.getItems().addAll(customTasks);
    }

//    public List<String> getVisibleTasks(ITaskDAO tasks, IPerson person){
//        List<String> visibleTasks = new java.util.ArrayList<>(List.of());
//        for (Task task : tasks.getUserTasks(person)){
//            if(Integer.parseInt(task.getUserId()) == userId){
//                visibleTasks.add(task.getTaskName());
//            }
//        }
//        return visibleTasks;
//    }

    private ListCell<Task> renderCell(ListView<Task> taskList) {
        return new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null || task.getId() <= 0 || task.getTaskDetails() == null ||
                        task.getDueDate() == null || task.getAssignedDate() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text taskDescription = new Text(task.getTaskDetails());
                    Text assignedDateText = new Text("Assigned: " + task.getAssignedDate().toString() + " ");
                    Text dueDateText = new Text("Due: " + task.getDueDate().toString() + " ");

                    Button completeButton = new Button("Complete");
                    completeButton.setOnAction(event -> {
                        if (taskList != null) {
                            taskList.getItems().remove(task);
                            taskDAO.delete(task.getId());  // Remove from the database
                        }
                    });

                    setGraphic(new HBox(taskDescription, assignedDateText, dueDateText, completeButton));
                }
            }
        };
    }

//    private ListView<Task> createTaskListView(Task... tasks){
//            ListView<Task> taskListView = new ListView<>();
//            taskListView.getItems().addAll(tasks);
//            taskListView.setCellFactory(this::renderCell);
//            return taskListView;
//    }


    @FXML
    public void initialize() {
        dropboxTasks.getPanes().clear();
        List<Task> taskList = (List<Task>) taskDAO.getUserTasks(person);
        if (taskList != null && !taskList.isEmpty()) {
            for (Task task : taskList) {
                switch (task.getCategory()) {
                    case DAILY:
                        dailyListView.getItems().add(task);
                        break;
                    case WEEKLY:
                        weeklyListView.getItems().add(task);
                        break;
                    case CUSTOM:
                        customListView.getItems().add(task);
                        break;
                }
                System.out.println("Task list items: " + task.getTaskDetails());
            }
        }

        // Render cells for daily, weekly, and custom tasks
        dailyListView.setCellFactory(this::renderCell);
        weeklyListView.setCellFactory(this::renderCell);
        customListView.setCellFactory(this::renderCell);

        // Update Titlepanes
        dailyTasks.setContent(dailyListView);
        weeklyTasks.setContent(weeklyListView);
        customTasks.setContent(customListView);

        // Get all panes into the single accordion
        dropboxTasks.getPanes().addAll(dailyTasks, weeklyTasks, customTasks);

        syncPerson(person);
    }

    private void addTask(Person person, String taskDescription, taskCategory category) {
        try {
            Task task = new Task(taskDescription, LocalDate.now(), LocalDate.now(), category);
            taskDAO.add(task);
            person.addTask(task);  // Assuming Person class has a method to add tasks
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category: " + category);
        }
    }
}


