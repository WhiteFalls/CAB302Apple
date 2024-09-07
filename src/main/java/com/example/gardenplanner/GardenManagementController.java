package com.example.gardenplanner;

import People.IPerson;
import Tasks.ITaskDAO;
import Tasks.MockTaskDAO;
import Tasks.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

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
        ListView<Task> taskList = person.getTasks();

        TitledPane taskPlane = new TitledPane("Assigned Tasks", taskList);

        TitledPane user = new TitledPane(person.getName(), new Accordion(taskPlane));
        //ArrayList<TitledPane> userTasks = new ArrayList<TitledPane>();

        return user;
    }

    private void syncContacts() {
        userDropBox.getPanes().clear();
        List<IPerson> people = personDAO.getAllPeople();
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
        syncContacts();
        // Select the first contact and display its information
        //contactsListView.getSelectionModel().selectFirst();
        /*Contact firstContact = contactsListView.getSelectionModel().getSelectedItem();
        if (firstContact != null) {
            selectContact(firstContact);
        }*/
    }
}
