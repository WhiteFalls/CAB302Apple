package com.example.gardenplanner.controller;

import Database.*;
import People.Garden;
import People.IPerson;

import Tasks.*;
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

import javax.sound.midi.SysexMessage;
import java.io.IOException;

import java.sql.SQLException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.sql.Connection;
import java.util.Map;

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
    private taskReassignmentManager taskManager;

    /**
     * Intialises the GardenToDoListController
     */
    public GardenToDoListController() {
        connection = DatabaseConnection.getConnection();
        personDAO = new PersonDAO();
        taskDAO = new TaskDAO();
        gardenDAO = new GardenDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);
        taskManager = taskReassignmentManager.getInstance();

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

        // reassignTasks(); // if we want to remove it from taskdao when completing it and tasklist
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

//    private void reassignTasks() {
//        for (Map.Entry<Task,LocalDate> entry : reassignedTasks.entrySet()){
//            Task task = entry.getKey();
//            LocalDate completedDate = entry.getValue();
//
//            // if current date is equal to or less than due date
//            if (!LocalDate.now().isAfter(task.getDueDate())){
//                Period period = Period.between(completedDate,LocalDate.now());
//
//                // if the period between when the task was completed equals its given timeframe
//                if ((task.getCategory().name().equals("DAILY") && period.getDays() >= 1) || (task.getCategory().name().equals("WEEKLY") && period.getDays() >= 7) ){
//                    // reassign tasks
//                    System.out.println("-------> " + period.getDays());
//                    Garden garden = gardenDAO.getGardenByTaskId(task);
//                    taskDAO.add(task, person, garden);
//                }
//            }
//            else{
//                reassignedTasks.remove(task); // task has passed due date
//            }
//        }
//    }

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

                    // check if completed button needs to be reset when task is reassigned or stay as green
                    for (Task name : taskManager.getReassignedTasks().keySet()) {
                        System.out.println(name);
                        System.out.println(": " + task);
                    }
                    if (taskManager.getReassignedTasks().containsKey(task)) {
                        System.out.println("--------------------------");
                        Period period = Period.between(taskManager.getReassignedTasks().get(task), LocalDate.now());
                        // If it's a daily task and has been completed a day ago
                        if ((task.getCategory().name().equals("DAILY") && period.getDays() >= 1) || (task.getCategory().name().equals("WEEKLY") && period.getDays() >= 7) ) {
                            completeButton = getButton(taskList,task);
                            resetCompletedButton(completeButton); // may not be needed
                        }
                        else{
                            completedButton(completeButton);
                        }
                    }

                    setGraphic(new HBox(taskDescription, assignedDateText, dueDateText, completeButton));
                }
            }
        };
    }

    private Button getButton(ListView<Task> taskList,Task task) {
        Button completeButton = new Button("Complete");

        completeButton.setOnAction(event -> {
            // check if current date is equal to or before due date
            if(!LocalDate.now().isAfter(task.getDueDate())){
                taskDAO.setCompletedDate(task,LocalDate.now());
                taskManager.addReassignedTask(task,LocalDate.now());
                completedButton(completeButton); // update button
                displayPopup("Task will be reassigned!");
            }
            else { // get rid of this to use reassign task
                taskDAO.delete(task); // so when pages refreshed it wont reappear  wont appear on gardenmanagement
                taskList.getItems().remove(task);
            }

        });

        return completeButton;
    }

    private void resetCompletedButton(Button completeButton) {
        completeButton.setStyle("-fx-background-color: rgba(232, 230, 225, 0.9);");
        completeButton.setText("Complete");
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

    private void completedButton(Button button) {
        button.setStyle("-fx-background-color: green; -fx-text-fill: black;");
        button.setText("Completed");
        button.setOnAction(event -> {displayPopup("Task will be reassigned!");
        });
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
