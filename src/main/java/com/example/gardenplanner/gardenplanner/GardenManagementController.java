package com.example.gardenplanner.gardenplanner;

import com.example.gardenplanner.model.*;
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
        ArrayList<IPerson> people = personDAO.getAllPeople();
        boolean hasContact = !people.isEmpty();

        if (hasContact) {
            ArrayList<TitledPane> userSections = new ArrayList<TitledPane>();

            for (IPerson person : personDAO.getAllPeople()){
                TitledPane user = createUserSection(person);
                userSections.add(user);
            }
            userDropBox.getPanes().addAll(userSections);
        }
        // Show / hide based on whether there are contacts
        userDropBox.setVisible(hasContact);
    }

    private ListView<HBox> createUserTasks(IPerson person)
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
                    String newTaskDetails = taskDetails.getCharacters().toString();
                    try
                    {
                        LocalDate newAssignedDate = assignedDate.getValue();
                        LocalDate newDueDate = dueDate.getValue();
                        Task newTask = new Task(newTaskDetails, newAssignedDate, newDueDate);
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
        return userOptionsSection;
    }

    private TitledPane createUserSection(IPerson person)
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

        VBox allTasks = new VBox(assignTasksButton, taskList);
        TitledPane taskPlane = new TitledPane("Assigned Tasks", allTasks);

        // Create dropdown for user options
        TitledPane userOptions = createUserOptions(person);

        TitledPane userTasks = new TitledPane(person.getName(), new Accordion(taskPlane, userOptions));
        //ArrayList<TitledPane> userTasks = new ArrayList<TitledPane>();


        return userTasks;
    }

    @FXML
    public void initialize() {
        for (IPerson person : personDAO.getAllPeople())
        {
            person.setTasks(taskDAO.getUserTasks(person));
        }
        syncPeople();
    }

    public void updateTask(IPerson person, Task oldTask, Task newTask)
    {
        if (oldTask.getId() != 0) {
            newTask.setId(oldTask.getId());
            person.editTask(newTask, oldTask);
            taskDAO.update(newTask);
            syncPeople();
        }
    }

    private void deleteTask(IPerson person, int id)
    {
        // Get the selected contact from the list view
        if (id != 0) {
            person.removeTask(id);
            taskDAO.delete(id);
            syncPeople();
        }
    }

    private void addTask(IPerson person)
    {
        Task task = new Task("New Task", LocalDate.now(), LocalDate.now());
        taskDAO.add(task);
        person.addTask(task);
        syncPeople();
    }

    private void removeUser(IPerson person)
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
