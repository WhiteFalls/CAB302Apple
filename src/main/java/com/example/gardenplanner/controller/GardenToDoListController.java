package com.example.gardenplanner.controller;

import People.IMockPerson;
import Tasks.ITaskDAO;
import Tasks.MockTaskDAO;
import Tasks.Task;
import Tasks.taskCategory;
import Database.IPersonDAO;
import Database.MockPersonDAO;
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
    private IMockPerson person;

    private ListView<Task> dailyListView = new ListView<>();
    private ListView<Task> weeklyListView = new ListView<>();
    private ListView<Task> customListView = new ListView<>();
    public GardenToDoListController() {
        taskDAO = new MockTaskDAO();
        userDAO = new MockPersonDAO();
        person = userDAO.getPerson(1);// subject to change // check if null
        addTask(person, "Plant Beans",taskCategory.WEEKLY);
    }


    // Methods

    @FXML
    private void testAddTask(ActionEvent event) {
        addTask(person, "Harvest Beans",taskCategory.DAILY);
        addTask(person, "Wash Beans",taskCategory.WEEKLY);
        addTask(person, "Boil Beans",taskCategory.WEEKLY);
        addTask(person, "DESTORY Beans",taskCategory.CUSTOM);
        syncPerson(person);
    }

    private void syncPerson(IMockPerson person) {
        dailyListView.getItems().clear();
        weeklyListView.getItems().clear();
        customListView.getItems().clear();

        dailyListView.getItems().addAll(taskDAO.getCategorisedTasks(person,taskCategory.DAILY)); // change --> for now should be wash beans
        weeklyListView.getItems().addAll(taskDAO.getCategorisedTasks(person,taskCategory.WEEKLY));
        customListView.getItems().addAll(taskDAO.getCategorisedTasks(person,taskCategory.CUSTOM));
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
                    Text assignedDateText = new Text("       Assigned: " + task.getAssignedDate().toString() + "      ") ;
                    Text dueDateText = new Text("       Due: " + task.getDueDate().toString() + "      ") ;

                    Button completeButton = new Button("Complete");

                    // Add an action event to the button

                    completeButton.setOnAction(event->{
                        if(taskList != null) {
                            taskList.getItems().remove(task);
                        }
                    });
                    setGraphic(new HBox(taskDescription, assignedDateText,dueDateText, completeButton));
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

    private void addTask(IMockPerson person, String taskDescription, taskCategory category)
    {
        try {

            //taskCategory categoryEnum = taskCategory.(category);
            Task task = new Task(taskDescription, LocalDate.now(), LocalDate.now(), category);
            person.addTask(task);
            taskDAO.add(task);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category: " + category);
        }
    }

}


