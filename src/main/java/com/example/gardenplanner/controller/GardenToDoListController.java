package com.example.gardenplanner.controller;

import Database.*;
import People.Garden;
import People.IPerson;
import People.Person;
import Tasks.ITaskDAO;
import Tasks.Task;
import Tasks.TaskDAO;
import Tasks.taskCategory;
import com.example.gardenplanner.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.sql.Connection;

public class GardenToDoListController {

    @FXML
    private Accordion dropboxTasks;
    @FXML
    private TitledPane weeklyTasks;
    @FXML
    private TitledPane dailyTasks;
    @FXML
    private TitledPane customTasks;

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

    private ListView<Task> dailyListView = new ListView<>();
    private ListView<Task> weeklyListView = new ListView<>();
    private ListView<Task> customListView = new ListView<>();

    private List<ListView<Task>> dailyListView2 = new ArrayList<>();
    private List<ListView<Task>> weeklyListView2 = new ArrayList<>();
    private List<ListView<Task>> customListView2 = new ArrayList<>();


    /**
     * Intialises the GardenToDoListController
     */
    public GardenToDoListController() {
        connection = DatabaseConnection.getConnection();
        personDAO = new PersonDAO(connection);
        taskDAO = new TaskDAO();
        gardenDAO = new GardenDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);

        // Retrieve user details from UserSession
        int personId = UserSession.getInstance().getPersonId();
        this.person = personDAO.getPerson(personId);



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

        for (int i = 0; i < personalGardens.size(); i++){ // this looks very inefficient propbably
            dailyListView2.add(new ListView<>());
            weeklyListView2.add(new ListView<>());
            customListView2.add(new ListView<>());

            List<Task> dailyLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.DAILY,personalGardens.get(i));
            List<Task> weeklyLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.WEEKLY,personalGardens.get(i));
            List<Task> customLists = taskDAO.getCategorisedTasksFromGarden(person,taskCategory.CUSTOM,personalGardens.get(i));

            dailyListView2.get(i).getItems().addAll(dailyLists);
            weeklyListView2.get(i).getItems().addAll(weeklyLists);
            customListView2.get(i).getItems().addAll(customLists);
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


    @FXML
    public void initialize() throws SQLException {
        // Check if taskDAO is initialized before using it
        if (taskDAO == null) {
            System.out.println("taskDAO is not initialized!");
            return;
        }
        personalGardens = gardenDAO.getGardensByUserId(person.getUserId());
        dropboxTasks.getPanes().clear();

        if (personalGardens != null && !personalGardens.isEmpty()) {
            // Loop through each garden for the user ID (getgardensbyuserID)
            // create a new title pane for the garden -> set text to garden name
            // create inner accordian for each task category
            // create titlepane for each task cateogory, and loading them by setcellfactory and setting them by setting content
            // add title panes to task category accordian by getpanes.addall(task category)
            // set the contents of the garden panes with the title panes above
            // set the contents of the testgarden accordian via getpanes.addall(gardenpanes)
            for (int i = 0; i < personalGardens.size(); i++){
                System.out.println("Going through garden: "+personalGardens.get(i).getGardenName());
                TitledPane gardenPane = new TitledPane();
                gardenPane.setText("Garden: " + personalGardens.get(i).getGardenName()); // will be users name for now (see main page)
                // create inner accordian for task categories
                Accordion taskCategories = new Accordion();

                dailyListView2.add(new ListView<>());
                weeklyListView2.add(new ListView<>());
                customListView2.add(new ListView<>());


                // add categorised tasks to list of listviews
                dailyListView2.get(i).getItems().addAll( taskDAO.getCategorisedTasksFromGarden(person,taskCategory.DAILY,personalGardens.get(i)));
                weeklyListView2.get(i).getItems().addAll(taskDAO.getCategorisedTasksFromGarden(person,taskCategory.WEEKLY,personalGardens.get(i)));
                customListView2.get(i).getItems().addAll(taskDAO.getCategorisedTasksFromGarden(person,taskCategory.CUSTOM,personalGardens.get(i)));
                // generate list of tasks (might not need to be seperated since sync does it for us..?)
                dailyListView2.get(i).setCellFactory(this::renderCell);
                weeklyListView2.get(i).setCellFactory(this::renderCell);
                customListView2.get(i).setCellFactory(this::renderCell);

                // generate titlepanes for corresponding task categories
                TitledPane dailyTasks = new TitledPane("Daily Tasks: ", dailyListView2.get(i));
                TitledPane weeklyTasks = new TitledPane("Weekly Tasks: ", weeklyListView2.get(i));
                TitledPane customTasks = new TitledPane("Custom Tasks: ",  customListView2.get(i));

                taskCategories.getPanes().addAll(dailyTasks, weeklyTasks, customTasks); // add tasks for the tasks accordian
                ScrollPane scrollPane = new ScrollPane((taskCategories));
                gardenPane.setContent(scrollPane); // add task accordian to each garden pane
                testGarden.getPanes().add(gardenPane); // set outer accordian
            }
            testGarden.getStyleClass().add("outer-accordion"); // make it look cooler i guess

        }
        // Render cells for daily, weekly, and custom tasks
        dailyListView.setCellFactory(this::renderCell);     // how is this working if the tasks are no longer categorized lol
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

    private ListView<Task> findTasksFromUserInGarden(Garden garden, IPerson person) throws SQLException {
        ObservableList<Task> taskItems = FXCollections.observableArrayList();
        ListView<Task> taskListView = new ListView<>(taskItems);
        String query = "SELECT * FROM Tasks WHERE garden_id = ? AND  user_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,garden.getGardenId());
            stmt.setInt(2,person.getUserId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Integer taskID = rs.getInt("task_id");
                String taskDetails = rs.getString("task_details");
                String category = rs.getString("category");
                LocalDate assignedDate = rs.getDate("assigned_date").toLocalDate();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                Task task = new Task(taskID,taskDetails,assignedDate,dueDate,taskCategory.valueOf(category));
                taskItems.add(task);
                System.out.println("Tasks: " + task.getTaskDetails());
            }
            taskListView.setItems(taskItems);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  taskListView;
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
