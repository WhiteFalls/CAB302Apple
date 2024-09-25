package com.example.gardenplanner.controller;

import Database.GardenDAO;
import Database.GardenUsersDAO;
import Database.IPersonDAO;
import Database.PersonDAO;
import People.Garden;
import People.IPerson;
import com.example.gardenplanner.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class GardenController {
    @FXML
    private TextField plantTextField;
    @FXML
    private TextField gardenNameText;
    @FXML
    private TextField gardenWidth;
    @FXML
    private TextField gardenHeight;
    @FXML
    private TextField plantedDateText;
    @FXML
    private TextField harvestDateText;
    @FXML
    private ComboBox colourDropDown;
    @FXML
    private Button confirmButton;

    private IPersonDAO personDAO;
    private GardenDAO gardenDAO;
    private GardenUsersDAO gardenUsersDAO;
    private Garden garden;
    private Connection connection;


    public GardenController()
    {
        personDAO = new PersonDAO(connection);
        gardenDAO = new GardenDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);
    }

    public void initialize() {
        // Retrieve user details from UserSession
        int personId = UserSession.getInstance().getPersonId();
        IPerson gardenOwner = personDAO.getPerson(personId);

        garden = gardenDAO.getGardenByUserId(personId);
        syncGarden();
    }

    private void syncGarden()
    {

    }
}