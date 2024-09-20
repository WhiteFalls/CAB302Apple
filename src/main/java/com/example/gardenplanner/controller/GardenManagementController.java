package com.example.gardenplanner.controller;

import Database.PersonDAO;
import People.IPerson;
import Tasks.ITaskDAO;
import Tasks.Task;
import Tasks.TaskDAO;
import Tasks.taskCategory;
import Database.IPersonDAO;
import Database.GardenDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.sql.Connection;
import java.util.Optional;

// currerntly, there is no seperation of gardens and garden id
// we havnt included garden DAO's methods, etc add garden
// what we need to do is a button that does the add a garden, auto increments the garden id and somehow save the garden
// id so we know which garden the user is on
// also, initialise a garden_users database (probbably from the garden db), where everytime you add a user to your
// garden youre referencing the garden id and user id and adding the user id to the table
//

public class GardenManagementController {
    // Fields
    @FXML
    private Accordion userDropBox;
    private Connection connection;

    private IPersonDAO personDAO;
    private GardenDAO gardenDAO;
    private ITaskDAO taskDAO;

    // Constructor
    public GardenManagementController() {
        taskDAO = new TaskDAO();
        personDAO = new PersonDAO(connection);
        gardenDAO = new GardenDAO();
    }

    // Methods

    @FXML
    /**
     * Allocates each task to their respective user and loads the user section
     */
    public void initialize() {
        for (IPerson person : personDAO.getAllPeople())
        {
            person.setTasks(taskDAO.getUserTasks(person));
        }
        syncPeople();
    }

    /**
     * Syncs all data by resetting the accordion and regenerating the user section
     */
    private void syncPeople() {
        userDropBox.getPanes().clear();

        ArrayList<IPerson> people = personDAO.getAllPeople();
        for (IPerson person : people)
        {
            person.setTasks(taskDAO.getUserTasks(person));
        }

        boolean hasPeople = !people.isEmpty();

        // Create dropdown for adding users
        TitledPane addUsers = getAddUsersSection();
        userDropBox.getPanes().add(addUsers);

        // Create dropdown for each user
        for (IPerson person : people){
            TitledPane user = createUserSection(person);
            user.getStyleClass().add("userSectionTitlePane");
            //userSections.add(user);
            userDropBox.getPanes().add(user);
        }

        if (hasPeople) {

        }
        // Show / hide based on whether there are people
        //userDropBox.setVisible(hasPeople);
    }

    private TitledPane getAddUsersSection() {
        Label addUsersLabel = new Label("Type in the user ID: ");
        TextField addUserText = new TextField();
        Button addUserButton = new Button("Add User");
        addUserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    int userId = Integer.parseInt(addUserText.getCharacters().toString());
                    addUser(userId);
                }
                catch (NumberFormatException e)
                {
                    displayPopup("User ID must be a valid number.");
                }
            }
        });

        HBox addUsersBox = new HBox(addUsersLabel, addUserText, addUserButton);
        addUsersBox.getStyleClass().add("hbox");

        TitledPane addUsers = new TitledPane("Add Users", addUsersBox);
        return addUsers;
    }

    /**
     * Creates a title pane for a user that contains their task and other user options
     * @param person The person whose tasks and options will be displayed
     * @return The title pane containing the user's tasks and options
     */
    private TitledPane createUserSection(IPerson person)
    {
        // Create dropdown for user tasks
        ListView<Task> taskList = new ListView<Task>();
        taskList.getStyleClass().add("taskList");
        taskList.getItems().addAll(person.getTasks());
        taskList.setCellFactory(this::renderCell);

        Button assignTasksButton = new Button("Assign Task");
        assignTasksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addTask(person);
                taskList.getItems().add(person.getNewestTask());
            }
        });
        VBox taskBox = new VBox(assignTasksButton, taskList);

        TitledPane userTasks = new TitledPane("Assigned Tasks", taskBox);
        userTasks.setMinHeight(200);
        userTasks.getStyleClass().add("userSectionTitlePane");


        // Create dropdown for user options
        TitledPane userOptions = createUserOptions(person);

        TitledPane userSection = new TitledPane(person.getName(), new Accordion(userTasks, userOptions));
        //ArrayList<TitledPane> userTasks = new ArrayList<TitledPane>();


        return userSection;
    }

    /**
     * Renders a customized cell for the garden manager with all the users in the garden and their tasks with the ability to
     * change or remove tasks
     * @param taskList The list of tasks for each user
     * @return A ListCell containing each users tasks
     */
    private ListCell<Task> renderCell(ListView<Task> taskList) {
        return new ListCell<Task>(){
            @Override
            protected void updateItem(Task task, boolean empty){
                super.updateItem(task,empty);

                if (empty || task == null || task.getId() <= 0 || task.getTaskDetails() == null ||
                        task.getDueDate() == null || task.getAssignedDate() == null) {
                    setText(null);
                    setGraphic(null);
                }
                else {

                    HBox taskBox = new HBox();
                    taskBox.getStyleClass().add("hbox");

                    Label taskId = new Label("ID: " + String.valueOf(task.getId()));
                    TextField taskDetails = new TextField(task.getTaskDetails());
                    DatePicker assignedDate = new DatePicker(task.getAssignedDate());
                    DatePicker dueDate = new DatePicker(task.getDueDate());
                    ComboBox<taskCategory> taskCategoryDrop = new ComboBox<taskCategory>();
                    taskCategoryDrop.getSelectionModel().select(task.getCategory());
                    taskCategoryDrop.getItems().addAll(taskCategory.DAILY, taskCategory.WEEKLY, taskCategory.CUSTOM);

                    Button confirmChangesButton = new Button("Confirm Changes");
                    confirmChangesButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try
                            {
                                String newTaskDetails = taskDetails.getCharacters().toString();
                                LocalDate newAssignedDate = assignedDate.getValue();
                                LocalDate newDueDate = dueDate.getValue();
                                taskCategory newCategory = taskCategoryDrop.getValue();
                                Task newTask = new Task(1, newTaskDetails, newAssignedDate, newDueDate, newCategory);
                                updateTask(task, newTask);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                displayPopup("Date must be in appropriate format.");
                            }
                        }
                    });

                    Button deleteTaskButton = new Button("Delete Task");
                    deleteTaskButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            if (displayConfirmPopup("Are you sure?"))
                            {
                                taskList.getItems().remove(task);
                                deleteTask(task);
                            }
                        }
                    });

                    taskBox.getChildren().addAll(taskId, taskDetails, assignedDate, dueDate, taskCategoryDrop, confirmChangesButton, deleteTaskButton);

                    setGraphic(taskBox);
                }
            }
        };

    }

    /**
     * Creates a section listing other non-task related options for the user
     * @param person The person of whom these options affect
     * @return A title pane containing all non-task related user options
     */
    private TitledPane createUserOptions(IPerson person) {
        Button removeUserButton = new Button("Remove User From Garden");
        removeUserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                removeUser(person);
            }
        });

        VBox userOptionsList = new VBox(removeUserButton);

        TitledPane userOptionsSection = new TitledPane("User Options", userOptionsList);
        userOptionsSection.getStyleClass().add("userSectionTitlePane");

        return userOptionsSection;
    }

    /**
     * Updates a user's task with a new task
     * @param oldTask The old task that is being replaced
     * @param newTask The new task that is replacing the old task
     */
    public void updateTask(Task oldTask, Task newTask)
    {
        if (newTask != null) {
            newTask.setId(oldTask.getId());
            oldTask.updateTask(newTask);
            taskDAO.update(oldTask, newTask);
        }
    }

    /**
     * Deletes a user's task
     * @param task The task to be deleted
     */
    private void deleteTask(Task task)
    {
        // Get the selected contact from the list view
            taskDAO.delete(task);
    }

    /**
     * Adds a new task to the user
     * @param person The user who is receiving the new task
     */
    private void addTask(IPerson person)
    {
        Task task = new Task(1, "New Task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        taskDAO.add(task,person);
        person.addTask(task);
    }

    /**
     * Removes a user from the garden
     * @param person The person to be removed
     */
    private void removeUser(IPerson person)
    {
        personDAO.deletePerson(person);
        syncPeople();
    }

    private void addUser(int id)
    {
        IPerson newPerson = personDAO.getPerson(id);
        if (newPerson == null)
        {
            displayPopup("No user with that ID exists");
        }
        else {
            syncPeople();
        }
    }

    /**
     * Displays a popup message on the screen
     * @param message The message inside the popup window
     */
    private void displayPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean displayConfirmPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            return true;
        }
        return false;
    }

    public void goBackToMainPage(ActionEvent event) {
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
