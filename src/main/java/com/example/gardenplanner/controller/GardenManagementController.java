package com.example.gardenplanner.controller;

import People.IMockPerson;
import People.MockPerson;
import Tasks.ITaskDAO;
import Tasks.MockTaskDAO;
import Tasks.Task;
import Tasks.taskCategory;
import Database.IPersonDAO;
import Database.MockPersonDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class GardenManagementController {
    // Fields
    @FXML
    private Accordion userDropBox;

    private IPersonDAO personDAO;
    private ITaskDAO taskDAO;

    // Constructor

    /**
     * Contructs the controller for the Garden Management page with the specified task and person DAOs
     */
    public GardenManagementController()
    {
        taskDAO = new MockTaskDAO();
        personDAO = new MockPersonDAO();
    }

    // Methods

    @FXML
    /**
     * Allocates each task to their respective user and loads the user section
     */
    public void initialize() {
        for (IMockPerson person : personDAO.getAllPeople())
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
        ArrayList<IMockPerson> people = personDAO.getAllPeople();
        boolean hasPeople = !people.isEmpty();

        if (hasPeople) {
            for (IMockPerson person : personDAO.getAllPeople()){
                TitledPane user = createUserSection(person);
                user.getStyleClass().add("userSectionTitlePane");
                //userSections.add(user);
                userDropBox.getPanes().add(user);
            }
        }
        // Show / hide based on whether there are people
        userDropBox.setVisible(hasPeople);
    }

    /**
     * Creates a title pane for a user that contains their task and other user options
     * @param person The person whose tasks and options will be displayed
     * @return The title pane containing the user's tasks and options
     */
    private TitledPane createUserSection(IMockPerson person)
    {
        TitledPane userTasks = new TitledPane();

        // Create dropdown for user tasks
        ListView<Task> taskList = new ListView<Task>();
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

        userTasks = new TitledPane("Assigned Tasks", taskBox);
        userTasks.getStyleClass().add("userSectionTitlePane");


        // Create dropdown for user options
        TitledPane userOptions = createUserOptions(person);

        TitledPane userSection = new TitledPane(person.getName(), new Accordion(userTasks, userOptions));
        //ArrayList<TitledPane> userTasks = new ArrayList<TitledPane>();


        return userSection;
    }

    private ListCell<Task> renderCell(ListView<Task> taskList) {
        return new ListCell<Task>(){
            @Override
            protected void updateItem(Task task, boolean empty){
                super.updateItem(task,empty);

                // Create Assign Task Button

               /* Label detailsLabel = new Label("Details");
                Label assignedDateLabel = new Label("Assigned Date");
                Label dueDateLabel = new Label("Due Date");

                HBox detailsTitle = new HBox(detailsLabel, assignedDateLabel, dueDateLabel);
                taskList.getItems().add(detailsTitle);*/

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

                    Button confirmChangesButton = new Button("Confirm Changes");
                    confirmChangesButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try
                            {
                                String newTaskDetails = taskDetails.getCharacters().toString();
                                LocalDate newAssignedDate = assignedDate.getValue();
                                LocalDate newDueDate = dueDate.getValue();
                                Task newTask = new Task(newTaskDetails, newAssignedDate, newDueDate, taskCategory.DAILY);
                                //updateTask(person, task, newTask);
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
                            taskList.getItems().remove(task);
                            //deleteTask(person, task);
                        }
                    });

                    taskBox.getChildren().addAll(taskId, taskDetails, assignedDate, dueDate, confirmChangesButton, deleteTaskButton);

                    setGraphic(taskBox);
                }
            }
        };

    }

    /**
     * Creates a list view containing all the user's tasks and options such as adding, deleting and editing tasks
     * @param person the person whose tasks are being listed
     * @return a vertical ListView of HBoxs, each detailing a unique task
     */
//    private ListView<HBox> createUserTasks(IMockPerson person)
//    {
//          }


    /**
     * Creates a section listing other non-task related options for the user
     * @param person The person of whom these options affect
     * @return A title pane containing all non-task related user options
     */
    private TitledPane createUserOptions(IMockPerson person) {
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
     * @param person The user whose task is being updated
     * @param oldTask The old task that is being replaced
     * @param newTask The new task that is replacing the old task
     */
    public void updateTask(IMockPerson person, Task oldTask, Task newTask)
    {
        if (newTask != null) {
            newTask.setId(oldTask.getId());
            person.editTask(newTask, oldTask);
            taskDAO.update(newTask);
            syncPeople();
        }
    }

    /**
     * Deletes a user's task
     * @param person The person whose task is being deleted
     * @param task The task to be deleted
     */
    private void deleteTask(IMockPerson person, Task task)
    {
        // Get the selected contact from the list view
            person.removeTask(task);
            taskDAO.delete(task);
    }

    /**
     * Adds a new task to the user
     * @param person The user who is receiving the new task
     */
    private void addTask(IMockPerson person)
    {
        Task task = new Task("New Task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        taskDAO.add(task);
        person.addTask(task);
    }

    /**
     * Removes a user from the garden
     * @param person The person to be removed
     */
    private void removeUser(IMockPerson person)
    {
        personDAO.deletePerson(person);
        syncPeople();
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
}
