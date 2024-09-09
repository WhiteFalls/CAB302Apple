package com.example.gardenplanner.controller;

import com.example.gardenplanner.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class GardenToDoListController {
         @FXML
        private TextArea todolistText;

        @FXML
        protected void addTask() {
            todolistText.appendText("Clean the garden!\n");
        }

        @FXML
        private ListView<Task> contactsListView;
        private ITaskDAO taskDAO;
        private IPersonDAO userDAO;
        public GardenToDoListController() {
            taskDAO = new MockTaskDAO();
            userDAO = new MockPersonDAO();
        }



        /**
         * Synchronizes the contacts list view with the contacts in the database.
         */
        private void syncContacts() {

        }

    @FXML
    public void initialize() {

    }
    }


