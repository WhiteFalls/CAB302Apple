package com.example.gardenplanner.controller;

import com.example.gardenplanner.model.*;
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
        //addTask(person);
    }


    // Methods
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
                System.out.println("BINGO");

                super.updateItem(task,empty);
                // Print task details to see when the cell is being updated
               // System.out.println("Update Cell: " + (task != null ? task.getTaskDetails() : "null task") + ", empty: " + empty);


                // if task is invalid
                if (empty || task == null || task.getId() <= 0 || task.getTaskDetails() == null ||
                    task.getDueDate() == null || task.getAssignedDate() == null) {
                    setText(null);
                    setGraphic(null);
                }
                else{
                    // Create custom layout for each cell
                    Text taskDescription = new Text(task.getTaskDetails());
                    Text dueDateText = new Text("       Due: " + task.getDueDate().toString());
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
                System.out.println("Task list items: " + task.getTaskDetails());
            }
        }


        //same for weekly, custom
        dailyListView.setCellFactory(this::renderCell);
        weeklyListView.setCellFactory(this::renderCell);
        customListView.setCellFactory(this::renderCell);


        //same for weekly, custom
        dailyTasks.setContent(dailyListView);
        weeklyTasks.setContent(weeklyListView);
        customTasks.setContent(customListView);


        //add all weekly,custom as well
        dropboxTasks.getPanes().addAll(dailyTasks,weeklyTasks,customTasks);



        syncPerson(person);
    }

    private void addTask(IPerson person)
    {
        Task task = new Task("New Task", LocalDate.now(), LocalDate.now(), Task.Category.DAILY);
        taskDAO.add(task);
        person.addTask(task);
       // syncPerson(person);
    }

//    public void removeTask(ActionEvent actionEvent) {
//    }
}


