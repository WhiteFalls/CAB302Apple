package com.example.gardenplanner.controller;

import Tasks.ITaskDAO;
import Tasks.MockTaskDAO;
import Tasks.Task;
import Tasks.taskCategory;
import Database.IPersonDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class GardenManagementController {
    // Fields
    @FXML
    private Accordion userDropBox;

    private IPersonDAO personDAO;
    private GardenDAO gardenDAO;
    private ITaskDAO taskDAO;

    // Constructor
    public GardenManagementController()
    {
        taskDAO = new MockTaskDAO();
        personDAO = new PersonDAO();
        gardenDAO = new GardenDAO();
    }

    // Methods
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

    private ListView<HBox> createUserTasks(IMockPerson person)
    {
        //Create dropdown for tasks
        ListView<HBox> taskList = new ListView<HBox>();

        for (Task task : person.getTasks()) {
            Label taskId = new Label(String.valueOf(task.getId()));
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
                        updateTask(person, task, newTask);
                    }
                    catch (DateTimeParseException e)
                    {
                        displayPopup("Date must be in appropriate format.");
                    }
                }
            });

            Button deleteTaskButton = new Button("Delete Task");
            deleteTaskButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    deleteTask(person, task.getId());
                }
            });

            HBox taskBox = new HBox(taskId, taskDetails, assignedDate, dueDate, confirmChangesButton, deleteTaskButton);
            taskList.getItems().add(taskBox);
        }
        return taskList;
    }

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

    private TitledPane createUserSection(IMockPerson person)
    {
        // Create Assign Task Button
        Button assignTasksButton = new Button("Assign Task");
        assignTasksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addTask(person);
            }
        });

        // Create dropdown for user tasks
        ListView<HBox> taskList = createUserTasks(person);

        VBox taskListAndButton = new VBox(assignTasksButton, taskList);

        TitledPane userTasks = new TitledPane("Assigned Tasks", taskListAndButton);
        userTasks.getStyleClass().add("userSectionTitlePane");


        // Create dropdown for user options
        TitledPane userOptions = createUserOptions(person);

        TitledPane userSection = new TitledPane(person.getName(), new Accordion(userTasks, userOptions));
        //ArrayList<TitledPane> userTasks = new ArrayList<TitledPane>();


        return userSection;
    }

    @FXML
    public void initialize() {
        for (IMockPerson person : personDAO.getAllPeople())
        {
            person.setTasks(taskDAO.getUserTasks(person));
        }
        syncPeople();
    }

    public void updateTask(IMockPerson person, Task oldTask, Task newTask)
    {
        if (oldTask.getId() != 0) {
            newTask.setId(oldTask.getId());
            person.editTask(newTask, oldTask);
            taskDAO.update(newTask);
            syncPeople();
        }
    }

    private void deleteTask(IMockPerson person, int id)
    {
        // Get the selected contact from the list view
        if (id != 0) {
            person.removeTask(id);
            taskDAO.delete(id);
            syncPeople();
        }
    }

    private void addTask(IMockPerson person)
    {
        Task task = new Task("New Task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        taskDAO.add(task);
        person.addTask(task);
        syncPeople();
    }

    private void removeUser(IMockPerson person)
    {
        personDAO.deletePerson(person);
        syncPeople();
    }

    private void displayPopup(String text)
    {
        Label message = new Label(text);
        Popup popup = new Popup();
        popup.getContent().add(message);
        popup.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_RIGHT);
        popup.show(popup.getOwnerWindow());
    }
}
