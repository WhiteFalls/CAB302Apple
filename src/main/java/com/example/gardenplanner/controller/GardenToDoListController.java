package com.example.gardenplanner.controller;

import com.example.gardenplanner.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class GardenToDoListController {
         @FXML
        private Accordion taskdropBox;
         private TitledPane dailyTasks, weeklyTasks, custom;


//        @FXML
//        protected void addTask() {
//            todolistText.appendText("Clean the garden!\n");
//        }
//
//        @FXML
//        private ListView<Task> contactsListView;
        private ITaskDAO taskDAO;
        private IPersonDAO userDAO;
        private IPerson person = userDAO.getPerson(1); // subject to change
        private ListView<Task> taskListView;
        public GardenToDoListController() {
            taskDAO = new MockTaskDAO();
            userDAO = new MockPersonDAO();
        }

    // Methods
    private void syncPerson(IPerson person) {
//        taskdropBox.getPanes().clear();
//        ArrayList<Task> userTasks = userDAO.getPerson(1).getTasks();
//        boolean hasTasks = userTasks.isEmpty();
//        if(!hasTasks) {
//            ArrayList<TitledPane> userSections = new ArrayList<TitledPane>();
//
//            for (ITask task : userTasks){
//               // TitledPane user = createUserTaskSection(task);
//            }
//        }
//        // Show / hide based on whether there are contacts
//        taskdropBox.setVisible(hasTasks);
        taskListView.getItems().clear();
        taskListView.getItems().addAll(taskDAO.getUserTasks(person));
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

    private ListCell<Task> renderCell(ListView<Task> task) {
        return new ListCell<Task>(){
            //@Override
            protected void updateItem(Task task, Boolean empty){
                super.updateItem(task,empty);

                // if task is invalid
                if (empty || task.getId() <= 0 || task.getTaskDetails() == null ||
                    task.getDueDate() == null || task.getAssignedDate() == null) {
                    setText(null);
                    setGraphic(null);
                }
                else{
                    // Create custom layout for each cell
                    Label taskLabel = new Label(task.getTaskDetails());
                    Button completeButton = new Button("Complete");

                    // Add an action event to the button

                    completeButton.setOnAction(event-> taskListView.getItems().remove(task));

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
        ListView<Task> daily = new ListView<>();
        // do the same for weekly, custom
        for (Task task : taskDAO.getUserTasks(person)) {//subject to change
            // switch (task.getcategory)
            daily.getItems().add(task);
            // else ListView<Task> weekly
            // else ListView<Task> custom
        }

        //same for weekly, custom
        daily.setCellFactory(this::renderCell);

        //same for weekly, custom
        dailyTasks.setContent(new HBox(daily));

        //add all weekly,custom as well
        taskdropBox.getPanes().addAll(dailyTasks);

        syncPerson(person);
    }

//    public void removeTask(ActionEvent actionEvent) {
//    }
}


