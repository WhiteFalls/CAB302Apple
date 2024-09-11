package com.example.gardenplanner.controller;

import com.example.gardenplanner.model.dao.IPersonDAO;
import com.example.gardenplanner.model.dao.ITaskDAO;
import com.example.gardenplanner.model.dao.MockPersonDAO;
import com.example.gardenplanner.model.dao.MockTaskDAO;
import com.example.gardenplanner.model.person.IPerson;
import com.example.gardenplanner.model.task.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

public class GardenToDoListController {
    @FXML
    private Accordion dropboxTasks;
    @FXML
    private TitledPane weeklyTasks;
    @FXML
    private TitledPane dailyTasks;
    @FXML
    private TitledPane customTasks;

    private ITaskDAO taskDAO;
    private IPersonDAO userDAO;
    private IPerson person;

    private ListView<Task> dailyListView = new ListView<>();
    private ListView<Task> weeklyListView = new ListView<>();
    private ListView<Task> customListView = new ListView<>();
    public GardenToDoListController() {
        taskDAO = new MockTaskDAO();
        userDAO = new MockPersonDAO();
        person = userDAO.getPerson(1);// subject to change // check if null
        addTask(person, "Plant Beans","Daily");
    }


    // Methods

    @FXML
    private void testAddTask(ActionEvent event) {
        addTask(person, "Harvest Beans","Daily");
        addTask(person, "Wash Beans","Weekly");
        addTask(person, "Boil Beans","Weekly");
        addTask(person, "DESTORY Beans","Custom");
        syncPerson(person);
    }

    private void syncPerson(IPerson person) {
        dailyListView.getItems().clear();
        weeklyListView.getItems().clear();
        customListView.getItems().clear();

        dailyListView.getItems().addAll(taskDAO.getCategorisedTasks(person,"DAILY")); // change --> for now should be wash beans
        weeklyListView.getItems().addAll(taskDAO.getCategorisedTasks(person,"WEEKLY"));
        customListView.getItems().addAll(taskDAO.getCategorisedTasks(person,"CUSTOM"));
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
        return new ListCell<Task>(){
            @Override
            protected void updateItem(Task task, boolean empty){
                super.updateItem(task,empty);

                // if task is invalid
                if (empty || task == null || task.getId() <= 0 || task.getTaskDetails() == null ||
                    task.getDueDate() == null || task.getAssignedDate() == null) {
                    setText(null);
                    setGraphic(null);
                }
                else{
                    // Create custom layout for each cell
                    Text taskDescription = new Text(task.getTaskDetails());
                    Text dueDateText = new Text("       Due: " + task.getDueDate().toString() + "      ") ;
                    Button completeButton = new Button("Complete");

                    // Add an action event to the button

                    completeButton.setOnAction(event->{
                        if(taskList != null) {
                            taskList.getItems().remove(task);

                        }
                    });
                    setGraphic(new HBox(taskDescription, dueDateText, completeButton));
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
        List<Task> taskList = taskDAO.getUserTasks(person);
        if (taskList != null && !taskList.isEmpty()) {

            for (Task task : taskDAO.getUserTasks(person)) {//subject to change
                switch (task.getCategory()) {
                    case DAILY: dailyListView.getItems().add(task); break;
                    case WEEKLY: weeklyListView.getItems().add(task); break;
                    case CUSTOM: customListView.getItems().add(task); break;
                }
                System.out.println("Task list items: " + task.getTaskDetails()); //-> dont know why this isnt on first
            }
        }


        // Render cells for daily, weekly and custom
        dailyListView.setCellFactory(this::renderCell);
        weeklyListView.setCellFactory(this::renderCell);
        customListView.setCellFactory(this::renderCell);


        // Update Titlepanes
        dailyTasks.setContent(dailyListView);
        weeklyTasks.setContent(weeklyListView);
        customTasks.setContent(customListView);

        // Get all panes into the single accordion
        dropboxTasks.getPanes().addAll(dailyTasks,weeklyTasks,customTasks);

        syncPerson(person);
    }

    private void addTask(IPerson person, String taskDescription, String category)
    {
        try {
            Task.Category categoryEnum = Task.Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category: " + category);
        }
        Task task = new Task(taskDescription, LocalDate.now(), LocalDate.now(), Task.Category.valueOf(category.toUpperCase()));
        taskDAO.add(task);
        person.addTask(task);
    }

}


