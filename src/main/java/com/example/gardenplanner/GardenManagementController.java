package com.example.gardenplanner;

import People.IPerson;
import Tasks.ITaskDAO;
import Tasks.MockTaskDAO;
import Tasks.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GardenManagementController {
    @FXML
    private Accordion userDropBox;

    private IPersonDAO personDAO;
    private ITaskDAO taskDAO;

    public GardenManagementController()
    {
        personDAO = new MockPersonDAO();
        taskDAO = new MockTaskDAO();
    }

    private TitledPane createUserSection(IPerson person)
    {
        // Create Assign Task Button
        Button assignTasks = new Button("Assign Task");
        //Create dropdown for tasks
        ListView<HBox> taskList = new ListView<HBox>();
        for (Task task : person.getTasks())
        {
            TextField taskDetails = new TextField(task.getTaskDetails());
            TextField assignedDate = new TextField(task.getAssignedDate().toString());
            TextField dueDate = new TextField(task.getDueDate().toString());
            Button confirmChangesButton = new Button("Confirm Changes");
            Button deleteTaskButton = new Button("Delete Task");

            HBox taskBox = new HBox(taskDetails, assignedDate, dueDate, confirmChangesButton, deleteTaskButton);
            taskList.getItems().add(taskBox);

        }

        VBox allTasks = new VBox(assignTasks, taskList);
        TitledPane taskPlane = new TitledPane("Assigned Tasks", allTasks);

        // Create dropdown for user options


        TitledPane user = new TitledPane(person.getName(), new Accordion(taskPlane));
        //ArrayList<TitledPane> userTasks = new ArrayList<TitledPane>();

        return user;
    }

    private void syncContacts() {
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

    @FXML
    public void initialize() {
        //contactsListView.setCellFactory(this::renderCell);
        syncContacts();
        // Select the first contact and display its information
        //contactsListView.getSelectionModel().selectFirst();
        /*Contact firstContact = contactsListView.getSelectionModel().getSelectedItem();
        if (firstContact != null) {
            selectContact(firstContact);
        }*/
    }
}
