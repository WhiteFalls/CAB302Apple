package com.gardenapplication.controller;

import Database.GardenUsersDAO;
import Database.PersonDAO;
import GardenCell.Garden;
import People.IPerson;
import Util.Popup;
import Database.ITaskDAO;
import Tasks.Task;
import Database.TaskDAO;
import Tasks.taskCategory;
import Database.IPersonDAO;
import Database.GardenDAO;
import com.gardenapplication.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

import java.sql.Connection;
import java.util.List;

/**
 * Controller for the garden management page
 */
public class GardenManagementController {
    // Fields
    @FXML
    private Accordion userDropBox;

    private Connection connection;

    private IPersonDAO personDAO;
    private GardenDAO gardenDAO;
    private GardenUsersDAO gardenUsersDAO;
    private ITaskDAO taskDAO;

    private Garden garden;

    // Constructor

    /**
     * Constructs a new GardenManagementController and initialises the task, person and garden DAOs.
     */
    public GardenManagementController() {
        taskDAO = new TaskDAO();
        personDAO = new PersonDAO();
        gardenDAO = new GardenDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);
    }

    // Methods

    @FXML
    /**
     * Allocates each task to their respective user and loads the user section
     */
    public void initialize() {
        // Retrieve user details from UserSession
        int personId = UserSession.getInstance().getPersonId();

        garden = gardenDAO.getGardenByUserId(personId);
        syncPeople();
    }

    /**
     * Syncs all data by resetting the accordion and regenerating the user section
     */
    private void syncPeople() {
        userDropBox.getPanes().clear();

        List<Integer> peopleIds = gardenUsersDAO.getPeopleIdsInGarden(garden);
        ArrayList<IPerson> people = new ArrayList<>();

        for (int id : peopleIds)
        {
            people.add(personDAO.getPerson(id));
        }

        for (IPerson person : people)
        {
            person.setTasks(taskDAO.getUserTasksFromGarden(person, garden));
        }

        boolean hasPeople = !people.isEmpty();

        // Create dropdown for adding users
        TitledPane addUsers = getAddUsersSection();
        userDropBox.getPanes().add(addUsers);

        // Create dropdown for each user
        for (IPerson person : people){
            TitledPane user = createUserSection(person);
            user.getStyleClass().add("userSectionTitlePane");
            userDropBox.getPanes().add(user);
        }

        if (hasPeople) {

        }
    }

    /**
     * Create the section to allow the manager to add users to their garden.
     * @return A title pane containing the add user option section
     */
    private TitledPane getAddUsersSection() {
        Label addUsersLabel = new Label("Type in the user ID: ");
        addUsersLabel.getStyleClass().add("task-title");

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
                    Popup.displayErrorPopup("User ID must be a valid number.");
                }
            }
        });

        VBox addUsersBox = new VBox(addUsersLabel, addUserText, addUserButton);
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
                addTask(person, taskList);
            }
        });

        VBox taskBox = new VBox(assignTasksButton, taskList);

        TitledPane userTasks = new TitledPane("Assigned Tasks", taskBox);
        userTasks.setMinHeight(300);
        userTasks.getStyleClass().add("userSectionTitlePane");
        userTasks.setStyle("-fx-background-color: #7fbd46;");

        // Create dropdown for user options
        TitledPane userOptions = createUserOptions(person);

        TitledPane userSection = new TitledPane(person.getName(), new Accordion(userTasks, userOptions));

        return userSection;
    }

    /**
     * Custom function that structures what each cell in a ListVeiw of Tasks should look like
     * @param taskList The task list that the cell is being added to
     * @return The cell that is being added to the task list
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

                    Label idTitle = new Label("Id:");
                    idTitle.getStyleClass().add("task-title");
                    Label taskId = new Label(String.valueOf(task.getId()));
                    VBox idBox = new VBox(idTitle, taskId);

                    Label detailsTitle = new Label("Task Details:");
                    detailsTitle.getStyleClass().add("task-title");
                    TextField taskDetails = new TextField(task.getTaskDetails());
                    VBox detailsBox = new VBox(detailsTitle, taskDetails);

                    Label aDateTitle = new Label("Assigned Date:");
                    aDateTitle.getStyleClass().add("task-title");
                    DatePicker assignedDate = new DatePicker(task.getAssignedDate());
                    VBox aDateBox = new VBox(aDateTitle, assignedDate);

                    Label dDateTitle = new Label("Due Date:");
                    dDateTitle.getStyleClass().add("task-title");
                    DatePicker dueDate = new DatePicker(task.getDueDate());
                    VBox dDateBox = new VBox(dDateTitle, dueDate);

                    Label categoryTitle = new Label("Category:");
                    categoryTitle.getStyleClass().add("task-title");
                    ComboBox<taskCategory> taskCategoryDrop = new ComboBox<taskCategory>();
                    VBox categoryBox = new VBox(categoryTitle, taskCategoryDrop);

                    taskCategoryDrop.getSelectionModel().select(task.getCategory());
                    taskCategoryDrop.getItems().addAll(taskCategory.DAILY, taskCategory.WEEKLY, taskCategory.CUSTOM);

                    Label confirmTitle = new Label("Confirm:");
                    confirmTitle.getStyleClass().add("task-title");
                    Button confirmChangesButton = new Button("Confirm Changes");
                    VBox confirmBox = new VBox(confirmTitle, confirmChangesButton);

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
                                Popup.displayErrorPopup("Date must be in appropriate format.");
                            }
                        }
                    });

                    Label deleteTitle = new Label("Delete:");
                    deleteTitle.getStyleClass().add("task-title");
                    Button deleteTaskButton = new Button("Delete Task");
                    VBox deleteBox = new VBox(deleteTitle, deleteTaskButton);

                    deleteTaskButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            if (Popup.displayConfirmPopup("Are you sure?"))
                            {
                                taskList.getItems().remove(task);
                                deleteTask(task);
                            }
                        }
                    });

                    taskBox.getChildren().addAll(idBox, detailsBox, aDateBox, dDateBox, categoryBox,
                            confirmBox, deleteBox);

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
    private void addTask(IPerson person, ListView<Task> taskList)
    {
        Task task = new Task("New Task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        taskDAO.add(task,person,garden);
        person.addTask(task);
        taskList.getItems().add(task);
    }

    /**
     * Removes a user from the garden
     * @param person The person to be removed
     */
    private void removeUser(IPerson person)
    {
        if (Popup.displayConfirmPopup("Are you sure you want to remove this person from your garden?"))
        {
            gardenUsersDAO.removePersonFromGarden(person, garden);
            taskDAO.deleteUserTasks(person, garden);
            syncPeople();
        }
    }

    /**
     * Adds a user to the garden
     * @param id The user id of the user to be added
     */
    private void addUser(int id)
    {
        if (gardenUsersDAO.getPeopleIdsInGarden(garden).contains(id))
        {
            Popup.displayErrorPopup("That user is already in your garden");
        }
        else
        {
            IPerson newPerson = personDAO.getPerson(id);
            if (newPerson == null)
            {
                Popup.displayErrorPopup("No user with that ID exists");
            }

            else {
                gardenUsersDAO.addPersonToGarden(newPerson, garden, "User");
                syncPeople();
            }
        }
    }


}
