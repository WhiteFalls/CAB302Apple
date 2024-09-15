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
    private ITaskDAO taskDAO;

    // Constructor
    public GardenManagementController()
    {
        taskDAO = new MockTaskDAO();
        personDAO = new MockPersonDAO();
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


    @FXML
    public void initialize() {
        for (IMockPerson person : personDAO.getAllPeople())
        {
            person.setTasks(taskDAO.getUserTasks(person));
        }
        syncPeople();
    }

    private TitledPane createUserSection(IMockPerson person)
    {
        // Create dropdown for user tasks
        ListView<HBox> taskList = createUserTasks(person);

        // Create dropdown for user tasks
        taskList = createUserTasks(person);

        TitledPane userTasks = new TitledPane("Assigned Tasks", taskList);
        userTasks.getStyleClass().add("userSectionTitlePane");


        // Create dropdown for user options
        TitledPane userOptions = createUserOptions(person);

        TitledPane userSection = new TitledPane(person.getName(), new Accordion(userTasks, userOptions));
        //ArrayList<TitledPane> userTasks = new ArrayList<TitledPane>();


        return userSection;
    }

    private ListView<HBox> createUserTasks(IMockPerson person)
    {
        //Create dropdown for tasks
        ListView<HBox> taskList = new ListView<HBox>();

        // Create Assign Task Button
        Button assignTasksButton = new Button("Assign Task");
        HBox assignTasksButtonBox = new HBox(assignTasksButton);
        taskList.getItems().add(assignTasksButtonBox);
        assignTasksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addTask(person);
            }
        });

        for (Task task : person.getTasks()) {
            HBox taskBox = new HBox();

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
                    taskList.getItems().remove(taskBox);
                    deleteTask(person, task);
                }
            });

            taskBox.getChildren().addAll(taskId, taskDetails, assignedDate, dueDate, confirmChangesButton, deleteTaskButton);
            taskList.getItems().add(taskBox);
        }
        return taskList;
    }

    private HBox getTaskBox(MockPerson person, Task task, ListView<HBox> taskList)
    {
        HBox taskBox = new HBox();

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
                taskList.getItems().remove(taskBox);
                deleteTask(person, task);
            }
        });

        taskBox.getChildren().addAll(taskId, taskDetails, assignedDate, dueDate, confirmChangesButton, deleteTaskButton);
        return taskBox;
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

    public void updateTask(IMockPerson person, Task oldTask, Task newTask)
    {
        if (oldTask.getId() != 0) {
            newTask.setId(oldTask.getId());
            person.editTask(newTask, oldTask);
            taskDAO.update(newTask);
            syncPeople();
        }
    }

    private void deleteTask(IMockPerson person, Task task)
    {
        // Get the selected contact from the list view
            person.removeTask(task);
            taskDAO.delete(task);
    }

    private void addTask(IMockPerson person)
    {
        Task task = new Task("New Task", LocalDate.now(), LocalDate.now(), taskCategory.DAILY);
        taskDAO.add(task);
        person.addTask(task);
    }

    private void removeUser(IMockPerson person)
    {
        personDAO.deletePerson(person);
        syncPeople();
    }

    private void displayPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
