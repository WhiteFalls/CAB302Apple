package com.gardenapplication.controller;

import Database.*;
import GardenCell.Garden;
import People.IPerson;

import Tasks.*;
import Util.Popup;
import com.gardenapplication.UserSession;

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

import java.sql.SQLException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

public class GardenToDoListController {

    @FXML
    private Accordion testGarden;

    private ITaskDAO taskDAO;
    private IPersonDAO personDAO;
    private IPerson person;
    private IGardenUsersDAO gardenUsersDAO;
    private List<Garden> personalGardens = new ArrayList<>();

    private Connection connection;

    private List<ListView<Task>> dailyListView = new ArrayList<>();
    private List<ListView<Task>> weeklyListView = new ArrayList<>();
    private List<ListView<Task>> customListView = new ArrayList<>();

    /**
     * Intialises the GardenToDoListController
     */
    public GardenToDoListController() {
        connection = DatabaseConnection.getConnection();
        personDAO = new PersonDAO();
        taskDAO = new TaskDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);

        // Retrieve user details from UserSession
        int personId = UserSession.getInstance().getPersonId();
        this.person = personDAO.getPerson(personId);
        personalGardens = gardenUsersDAO.getAllGardenByUserId(person.getUserId());

        // Sync tasks with the person
        syncPerson(this.person);
    }

    /**
     * Button call that re-syncs the task data
     */
    @FXML
    private void testAddTask() {
        syncPerson(person);
    }

    /**
     * Resets the page and adds back all the task data organised in terms of task category
     * @param person The person whom the tasks belong to
     */
    private void syncPerson(IPerson person) {
        dailyListView.clear();
        weeklyListView.clear();
        customListView.clear();
        for (int i = 0; i < personalGardens.size(); i++){

            // check for out of bounds and create more lists if needed
            if (i >= dailyListView.size()) {
                dailyListView.add(new ListView<>());
                weeklyListView.add(new ListView<>());
                customListView.add(new ListView<>());
            }

            List<Task> dailyLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.DAILY,personalGardens.get(i));
            List<Task> weeklyLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.WEEKLY,personalGardens.get(i));
            List<Task> customLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.CUSTOM,personalGardens.get(i));

            // each list is a task from a separate garden
            dailyListView.get(i).getItems().addAll(dailyLists);
            weeklyListView.get(i).getItems().addAll(weeklyLists);
            customListView.get(i).getItems().addAll(customLists);
        }

    }


    /**
     * Custom function that structures what each cell in a ListVeiw of Tasks should look like
     * @param taskList The task list that the cell is being added to
     * @return The cell that is being added to the task list
     */
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

                    Button completeButton = getButton(taskList,task);

                    // if task has been completed
                    if (taskDAO.getCompletedDate(task) != null){
                        // check period when task was completed to its category
                        Period period = Period.between(taskDAO.getCompletedDate(task), LocalDate.now());
                        taskCategory category = task.getCategory();
                        // If it's a daily task and has been completed a day ago or a weekly task and completed a week ago
                        if ((category.name().equals("DAILY") && period.getDays() >= 1) || (category.name().equals("WEEKLY") && period.getDays() >= 7 )) {
                           // reset button
                            completeButton = getButton(taskList,task);
                            resetCompletedButton(completeButton);
                            taskDAO.setCompletedDate(task, null);
                        }
                        // if task is not due
                        else if (!LocalDate.now().isAfter(task.getDueDate())){
                            completedButton(completeButton);
                        }
                        // task is already due
                        else{
                            taskDAO.delete(task);
                            taskList.getItems().remove(task);
                        }
                    }
                    setGraphic(new HBox(taskDescription, assignedDateText, dueDateText, completeButton));
                }
            }
        };
    }

    /**
     * Creates a button with the ability to complete tasks
     * @param taskList The list of tasks the user contains
     * @param task The task for which the button can complete
     * @return The button to complete the given task
     */
    private Button getButton(ListView<Task> taskList,Task task) {
        Button completeButton = new Button("Complete");

        completeButton.setOnAction(event -> {
            // check if current date is equal to or before due date
            if(!LocalDate.now().isAfter(task.getDueDate())){
                taskDAO.setCompletedDate(task,LocalDate.now());
                completedButton(completeButton); // update button
                Popup.showAlert("Task","Task will be reassigned!");
            }
            else { // get rid of this to use reassign task
                taskDAO.delete(task);
                taskList.getItems().remove(task);
            }

        });

        return completeButton;
    }

    /**
     * Changes the button back into complete and its colour back to its original form
     * @param completeButton
     */
    private void resetCompletedButton(Button completeButton) {
        completeButton.setStyle("-fx-background-color: rgba(232, 230, 225, 0.9);");
        completeButton.setText("Complete");
    }
    /**
     * Changes the button name to be completed and its color to green
     * @param button
     */
    private void completedButton(Button button) {
        button.setStyle("-fx-background-color: green; -fx-text-fill: black;");
        button.setText("Completed");
        button.setOnAction(event -> {
            Popup.showAlert("Task","Task will be reassigned!");
        });
    }

    /**
     * Initializes the garden to do list controller
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException {
        // Check if taskDAO is initialized before using it
        if (taskDAO == null) {
            System.out.println("taskDAO is not initialized!");
            return;
        }
        testGarden.getPanes().clear();

        syncPerson(person);

        if (personalGardens != null && !personalGardens.isEmpty()) {
            for (int i = 0; i < personalGardens.size(); i++){
                TitledPane gardenPane = new TitledPane();
                gardenPane.setText("Garden: " + personalGardens.get(i).getGardenName());

                // create inner accordian for task categories
                Accordion taskCategories = new Accordion();
                TitledPane dailyTasks;
                TitledPane weeklyTasks;
                TitledPane customTasks;
                // generate list of tasks
                if(!dailyListView.get(i).getItems().isEmpty()){
                    dailyListView.get(i).setCellFactory(this::renderCell);
                    dailyTasks = new TitledPane("Daily Tasks: ", dailyListView.get(i));
                }
                else{
                    Text emptyDailyTask = new Text("There are no daily tasks here currently: " + LocalDate.now());
                    dailyTasks = new TitledPane("Daily Tasks: ", emptyDailyTask);
                }
                if(!weeklyListView.get(i).getItems().isEmpty()){
                    weeklyListView.get(i).setCellFactory(this::renderCell);
                    weeklyTasks = new TitledPane("Weekly Tasks: ", weeklyListView.get(i));
                }
                else{
                    Text emptyWeeklyTask = new Text("There are no weekly tasks here currently: " + LocalDate.now());
                    weeklyTasks = new TitledPane("Weekly Tasks: ", emptyWeeklyTask);
                }
                if(!customListView.get(i).getItems().isEmpty()) {
                    customListView.get(i).setCellFactory(this::renderCell);
                    customTasks = new TitledPane("Custom Tasks: ", customListView.get(i));
                }
                else{
                    Text emptyCustomTask = new Text("There are no custom tasks here currently: " + LocalDate.now());
                    customTasks = new TitledPane("Custom Tasks: ", emptyCustomTask);
                }
                taskCategories.getPanes().addAll(dailyTasks, weeklyTasks, customTasks); // add tasks for the tasks accordian
                gardenPane.setContent(taskCategories); // add task accordian to each garden pane
                testGarden.getPanes().add(gardenPane); // set outer accordian
            }
            testGarden.getStyleClass().add("outer-accordion");
        }
    }
}
