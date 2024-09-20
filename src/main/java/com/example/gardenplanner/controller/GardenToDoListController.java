package com.example.gardenplanner.controller;

import People.IPerson;
import Tasks.ITaskDAO;
import Tasks.Task;
import Tasks.TaskDAO;
import Tasks.taskCategory;
import Database.IPersonDAO;
import Database.PersonDAO;
import com.example.gardenplanner.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import java.sql.Connection;

// I need to implement a button that allows users to switch between their gardens, or if they are in alot of gardens
// somehow make a slider or something
// keep track of what garden each user is in through the garden_users db, and have a method of switching through the
// gardens and letting the controller know which garden the user is on
// each user also has a specific task in the garden, so you will also need to update that
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
    private IPersonDAO personDAO;
    private IPerson person;

    private Connection connection;

    private ListView<Task> dailyListView = new ListView<>();
    private ListView<Task> weeklyListView = new ListView<>();
    private ListView<Task> customListView = new ListView<>();


    // Initialize the controller with the necessary objects
    public GardenToDoListController() {
        personDAO = new PersonDAO(connection);
        taskDAO = new TaskDAO();

        // Retrieve user details from UserSession
        int personId = UserSession.getInstance().getPersonId();
        this.person = personDAO.getPerson(personId);



        // Sync tasks with the person
        syncPerson(this.person);
    }

    @FXML
    private void testAddTask() {
        syncPerson(person);
    }

    // loop through tasks for each garden
    private void syncPerson(IPerson person) {
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

    private ListCell<Task> renderCell(ListView<Task> taskList) {
        return new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text taskDescription = new Text(task.getTaskDetails());
                    Text assignedDateText = new Text(": Assigned: " + task.getAssignedDate().toString());
                    Text dueDateText = new Text(" Due: " + task.getDueDate().toString());

                    Button completeButton = new Button("Complete");
                    completeButton.setOnAction(event -> {
                        taskList.getItems().remove(task);
                        taskDAO.delete(task);
                    });

                    setGraphic(new HBox(taskDescription, assignedDateText, dueDateText, completeButton));
                }
            }
        };
    }

    // have a fid for the garden accordian
    // for each garden,
    // create a titlepane (getgardenbyuserid -- logged in user) and assign it a title
    // create a secondary accordian for of the daily weekly custom tasks
    // create a list<tasks> of logged in user tasks based on the garden id (maybe like getusertasksFromGarden)
    //      > query task db based on userid and garden id and add them to a list
    // sort tasks into daily ,weekly, custom and do the render cell
    // add panes to task category accordian (getpanes.addall
    // set the content of the secondary accordian to the titlepane created above
    // add the titlepane to the garden accordian by getpanes.addall
    // end for loop

    @FXML
    public void initialize() {
        // Check if taskDAO is initialized before using it
        if (taskDAO == null) {
            System.out.println("taskDAO is not initialized!");
            return;
        }

        dropboxTasks.getPanes().clear();
//        List<Task> taskList = taskDAO.getUserTasks(person);
//        if (taskList != null && !taskList.isEmpty()) {
//            for (Task task : taskList) {
//                switch (task.getCategory()) {
//                    case DAILY:
//                        dailyListView.getItems().add(task);
//                        break;
//                    case WEEKLY:
//                        weeklyListView.getItems().add(task);
//                        break;
//                    case CUSTOM:
//                        customListView.getItems().add(task);
//                        break;
//                }
//            }
//        }

        // Render cells for daily, weekly, and custom tasks
        dailyListView.setCellFactory(this::renderCell);
        weeklyListView.setCellFactory(this::renderCell);
        customListView.setCellFactory(this::renderCell);

        // Update TitledPanes
        dailyTasks.setContent(dailyListView);
        weeklyTasks.setContent(weeklyListView);
        customTasks.setContent(customListView);

        dropboxTasks.getPanes().addAll(dailyTasks, weeklyTasks, customTasks);
    }

    private void addTask(IPerson person, String taskDescription, taskCategory category) {
        try {
            Task task = new Task(1, taskDescription, LocalDate.now(), LocalDate.now(), category);
            taskDAO.add(task,person);
            person.addTask(task);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category: " + category);
        }
    }

    @FXML
    private void goBackToMainPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gardenplanner/main-page.fxml"));
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, 1600, 800);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
