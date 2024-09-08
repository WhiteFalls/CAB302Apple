package com.example.gardenplanner;

import People.IPerson;
import Tasks.ITaskDAO;
import Tasks.MockTaskDAO;
import Tasks.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
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
        personDAO = new MockPersonDAO();
        taskDAO = new MockTaskDAO();
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
            TextField assignedDate = new TextField(task.getAssignedDate().toString());
            TextField dueDate = new TextField(task.getDueDate().toString());

            Button confirmChangesButton = new Button("Confirm Changes");
            confirmChangesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String newTaskDetails = taskDetails.getCharacters().toString();
                    CharSequence newAssignedDate = assignedDate.getCharacters();
                    CharSequence newDueDate = dueDate.getCharacters();
                    Task newTask = new Task(newTaskDetails, LocalDate.parse(newAssignedDate), LocalDate.parse(newDueDate));
                    updateTask(person, task.getId(), newTask);
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
        //contactsListView.setCellFactory(this::renderCell);
        syncPeople();
        // Select the first contact and display its information
        //contactsListView.getSelectionModel().selectFirst();
        /*Contact firstContact = contactsListView.getSelectionModel().getSelectedItem();
        if (firstContact != null) {
            selectContact(firstContact);
        }*/
    }

    public void updateTask(IPerson person, int id, Task newTask)
    {
        if (id != 0) {
            newTask.setId(id);
            person.editTask(newTask);
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
        person.addTask(task);
        taskDAO.add(task);
        syncPeople();
    }

    private void removeUser(IPerson person)
    {
        personDAO.deletePerson(person);
        syncPeople();
    }
}
