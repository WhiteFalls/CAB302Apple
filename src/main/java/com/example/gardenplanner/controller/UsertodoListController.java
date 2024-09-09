package com.example.gardenplanner.controller;

import com.example.gardenplanner.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class UsertodoListController {
    @FXML
    private TextArea todolistText;

    @FXML
    protected void addTask() {
        todolistText.appendText("Clean the garden!\n");
    }

    @FXML
    private ListView<Task> contactsListView;
    private IMockUserTasksDAO taskDAO;
    private IMockUsersDAO userDAO;
    public UsertodoListController() {
        taskDAO = new MockUserTasksDAO();
        userDAO = new MockUsers();
    }

    /**
     * Renders a cell in the contacts list view by setting the text to the contact's full name.
     * @param taskListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<Task> renderCell(ListView<Task> taskListView) {
        return new ListCell<>() {
            /**
             * Updates the item in the cell by setting the text to the contact's full name.
             * @param task The contact to update the cell with.
             * @param empty Whether the cell is empty.
             */
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                // If the cell is empty, set the text to null, otherwise set it to the contact's full name
                if (empty || task == null || task.getTaskName() == null) {
                    setText(null);
                } else {
                    // Check if the task's user ID matches the current user's ID
                    int currentUserId = userDAO.getCurrentUserId(1); // will need a way to find logged-in users
                    if (Integer.parseInt(task.getUserId()) == currentUserId) {
                        setText(task.getTaskName());
                    } else {
                        setText(null);  // Don't render tasks for other users
                    }

                }
            }
        };
    }

    /**
     * Synchronizes the contacts list view with the contacts in the database.
     */
    private void syncContacts() {
        contactsListView.getItems().clear();
        contactsListView.getItems().addAll(taskDAO.getAllTasks());
    }

    @FXML
    public void initialize() {
        contactsListView.setCellFactory(this::renderCell);
        //contactsListView.getItems().addAll(contactDAO.getAllContacts());
        syncContacts();
    }
}
