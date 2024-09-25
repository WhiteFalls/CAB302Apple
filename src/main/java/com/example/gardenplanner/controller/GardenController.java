package com.example.gardenplanner.controller;

import Database.*;
import GardenCell.GardenCell;
import People.Garden;
import People.IPerson;
import com.example.gardenplanner.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Connection;

public class GardenController {
    @FXML
    private Label gardenTitle;
    @FXML
    private GridPane gardenGrid;
    @FXML
    private TextField gardenNameText;
    @FXML
    private TextField gardenWidth;
    @FXML
    private TextField gardenHeight;
    @FXML
    private TextField plantTextField;
    @FXML
    private DatePicker plantedDatePicker;
    @FXML
    private DatePicker harvestDatePicker;
    @FXML
    private ColorPicker colourDropDown;
    @FXML
    private Button confirmButton;

    private IPersonDAO personDAO;
    private GardenDAO gardenDAO;
    private GardenMapDAO gardenMapDAO;
    private GardenUsersDAO gardenUsersDAO;
    private Garden garden;
    private Connection connection;


    public GardenController()
    {
        personDAO = new PersonDAO(connection);
        gardenDAO = new GardenDAO();
        gardenUsersDAO = new GardenUsersDAO(connection);
        gardenMapDAO = new GardenMapDAO();
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
        gardenTitle.setText(garden.getGardenName());
        syncGardenDetails();

        GardenCell[][] cells = gardenMapDAO.getGardenCells(garden);
        for (int x = 0; x < cells.length; x++)
        {
            for (int y = 0; y < cells[0].length; y++)
            {
                GardenCell cell = cells[x][y];
                Button plotButton = new Button(cell.getPlant());
                plotButton.setTextFill(cell.getColour());
                plotButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                     displayCell(cell);
                    }
                });
                gardenGrid.add(plotButton, x, y);
            }
        }

    }

    private void syncGardenDetails()
    {
        gardenNameText.setText(garden.getGardenName());
        gardenWidth.setText(Integer.toString(garden.getWidth()));
        gardenHeight.setText(Integer.toString(garden.getHeight()));
        plantTextField.setPromptText("Not Selected");
        plantTextField.setDisable(true);
        plantedDatePicker.setDisable(true);
        harvestDatePicker.setDisable(true);
        colourDropDown.setDisable(true);
        confirmButton.setDisable(true);
    }

    private void displayCell(GardenCell cell)
    {
        plantTextField.setText(cell.getPlant());
        plantedDatePicker.setValue(cell.getPlantedDate());
        harvestDatePicker.setValue(cell.getHarvestDate());
        colourDropDown.setValue(cell.getColour());
    }
}
