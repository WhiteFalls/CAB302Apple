package com.example.gardenplanner.controller;

import Database.*;
import People.Garden;
import People.IPerson;

import Tasks.ITaskDAO;
import Tasks.Task;
import Tasks.TaskDAO;
import Tasks.taskCategory;
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
import org.testng.reporters.jq.TestNgXmlPanel;

import java.io.IOException;

import java.sql.SQLException;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
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
    private IGardenDAO gardenDAO;
    private List<Garden> personalGardens = new ArrayList<>();
    private List<List<Task>> taskListForGardens = new ArrayList<>();

    private Connection connection;

    private List<ListView<Task>> dailyListView2 = new ArrayList<>();
    private List<ListView<Task>> weeklyListView2 = new ArrayList<>();
    private List<ListView<Task>> customListView2 = new ArrayList<>();
    private List<Task> reassignTaskList = new ArrayList<>();


    /**
     * Intialises the GardenToDoListController
     */
    public GardenToDoListController() {
        connection = DatabaseConnection.getConnection();
        personDAO = new PersonDAO();
        taskDAO = new TaskDAO();
        gardenDAO = new GardenDAO();
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
        dailyListView2.clear();
        weeklyListView2.clear();
        customListView2.clear();
        for (Task task : reassignTaskList){
            if (task.getDueDate().compareTo(LocalDate.now()) <= 0){ // before due date AND is a daily/weekly
                // add task to dao ---------------------------> prob store task and garden as hashmap
            }
        }
        if (!personalGardens.isEmpty()){
                for (int i = 0; i < personalGardens.size(); i++){ // this looks very inefficient propbably

                    // check for out of bounds and create if more lists if needed
                    if (i >= dailyListView2.size()) {
                        dailyListView2.add(new ListView<>());
                        weeklyListView2.add(new ListView<>());
                        customListView2.add(new ListView<>());
                    }

                    List<Task> dailyLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.DAILY,personalGardens.get(i));
                    List<Task> weeklyLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.WEEKLY,personalGardens.get(i));
                    List<Task> customLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.CUSTOM,personalGardens.get(i));

                    // each list is a task from a separate garden
                    dailyListView2.get(i).getItems().addAll(dailyLists);
                    weeklyListView2.get(i).getItems().addAll(weeklyLists);
                    customListView2.get(i).getItems().addAll(customLists);
                }
        }
        else{
            // display error --> its never gonna be empty due to checks  before hand
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

                    setGraphic(new HBox(taskDescription, assignedDateText, dueDateText, completeButton));
                }
            }
        };
    }

    private Button getButton(ListView<Task> taskList,Task task) {
        Button completeButton = new Button("Complete");
        completeButton.setOnAction(event -> {
            // check if task is not over due yet
            if(task.getDueDate().compareTo(LocalDate.now()) <= 0){
                reassignTaskList.add(task);
                displayPopup("Task will be reassigned!");
            }
            taskDAO.delete(task); // so when pages refreshed it wont reappear
            taskList.getItems().remove(task);
        });
        return completeButton;
    }


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
                // generate list of tasks (might not need to be seperated since sync does it for us..?)
                if(!dailyListView2.get(i).getItems().isEmpty()){
                    dailyListView2.get(i).setCellFactory(this::renderCell);
                    dailyTasks = new TitledPane("Daily Tasks: ", dailyListView2.get(i));
                }
                else{
                    Text emptyDailyTask = new Text("There are no daily tasks here currently: " + LocalDate.now());
                    dailyTasks = new TitledPane("Daily Tasks: ", emptyDailyTask);
                }
                if(!weeklyListView2.get(i).getItems().isEmpty()){
                    weeklyListView2.get(i).setCellFactory(this::renderCell);
                    weeklyTasks = new TitledPane("Weekly Tasks: ", weeklyListView2.get(i));
                }
                else{
                    Text emptyWeeklyTask = new Text("There are no weekly tasks here currently: " + LocalDate.now());
                    weeklyTasks = new TitledPane("Weekly Tasks: ", emptyWeeklyTask);
                }
                if(!customListView2.get(i).getItems().isEmpty()) {
                    customListView2.get(i).setCellFactory(this::renderCell);
                    customTasks = new TitledPane("Custom Tasks: ", customListView2.get(i));
                }
                else{
                    Text emptyCustomTask = new Text("There are no custom tasks here currently: " + LocalDate.now());
                    customTasks = new TitledPane("Custom Tasks: ", emptyCustomTask);
                }
                taskCategories.getPanes().addAll(dailyTasks, weeklyTasks, customTasks); // add tasks for the tasks accordian
                ScrollPane scrollPane = new ScrollPane((taskCategories));
                gardenPane.setContent(scrollPane); // add task accordian to each garden pane
                testGarden.getPanes().add(gardenPane); // set outer accordian
            }
            testGarden.getStyleClass().add("outer-accordion"); // make it look cooler i guess
        }
    }


    private void displayPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Garden");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets scene back to the main page of the application
     * @param event The event that triggers the page change
     */
    @FXML
    private void goBackToMainPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gardenplanner/main-page.fxml"));
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, 1200, 600);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
