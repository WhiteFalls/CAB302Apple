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
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.util.Optional;

import static java.lang.Integer.MAX_VALUE;

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

                Button plotButton = createPlotButton(cell);

                gardenGrid.add(plotButton, x, y);
            }
        }

    }

    private Button createPlotButton(GardenCell cell)
    {
        Button plotButton = new Button(cell.getPlant());
        String colour_s = colorToString(cell.getColour());
        plotButton.setStyle("-fx-background-color:" + colour_s);
        plotButton.maxWidth(100000000);
        plotButton.maxHeight(100000000);
        plotButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                displayCell(cell);
            }
        });
        return plotButton;
    }

    private static String colorToString(Color color) {
        String colour_s = color.toString();
        colour_s = colour_s.substring(2, colour_s.length());
        colour_s = "#" + colour_s;
        return colour_s;
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
        plantTextField.setDisable(false);

        plantedDatePicker.setValue(cell.getPlantedDate());
        plantedDatePicker.setDisable(false);

        harvestDatePicker.setValue(cell.getHarvestDate());
        harvestDatePicker.setDisable(false);

        colourDropDown.setValue(cell.getColour());
        colourDropDown.setDisable(false);

        confirmButton.setDisable(false);

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GardenCell newCell = new GardenCell(plantTextField.getText(),
                        cell.getX(),
                        cell.getY(),
                        plantedDatePicker.getValue(),
                        harvestDatePicker.getValue(),
                        colourDropDown.getValue());
                updateCell(cell, newCell);
            }
        });
    }

    private void updateCell(GardenCell oldCell, GardenCell newCell)
    {
        gardenMapDAO.updateCell(newCell);
        Button plotButton = createPlotButton(newCell);
        gardenGrid.getChildren().remove(oldCell);
        gardenGrid.add(plotButton, newCell.getX(), newCell.getY());
    }
    /**
     * Displays a popup message on the screen
     * @param message The message inside the popup window
     */
    private void displayPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation popup on the screen
     * @param message The message to be shown in the popup
     * @return A boolean value based on the user's decision to confirm or deny the action
     */
    private boolean displayConfirmPopup(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            return true;
        }
        return false;
    }
}
