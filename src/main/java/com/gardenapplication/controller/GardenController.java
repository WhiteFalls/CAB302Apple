package com.gardenapplication.controller;

import Database.*;
import GardenCell.GardenCell;
import GardenCell.Garden;
import People.IPerson;
import com.gardenapplication.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import Util.Popup;


import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class GardenController {
    @FXML
    private Label gardenTitle;
    @FXML
    private ScrollPane gardenScroll;
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

    private GardenDAO gardenDAO;
    private GardenMapDAO gardenMapDAO;
    private Garden garden;
    private Connection connection;


    /**
     * Garden controller constructor
     */
    public GardenController()
    {
        connection = DatabaseConnection.getConnection();
        gardenDAO = new GardenDAO();
        gardenMapDAO = new GardenMapDAO();
    }

    /**
     * Initializes the garden controller
     */
    public void initialize() {
        // Retrieve user details from UserSession
        int personId = UserSession.getInstance().getPersonId();
        gardenWidth.setMinWidth(30);
        gardenHeight.setMinWidth(30);
        plantedDatePicker.setMinWidth(250);
        harvestDatePicker.setMinWidth(250);


        garden = gardenDAO.getGardenByUserId(personId);
        syncGarden();
    }

    /**
     * Retrieves information for the garden and synchronises it to the controller
     */
    private void syncGarden()
    {
        gardenGrid.getRowConstraints().clear();
        gardenGrid.getColumnConstraints().clear();
        gardenGrid.getChildren().clear();
        gardenTitle.setText(garden.getGardenName());
        syncGardenDetails();

        for (int row = 0 ; row < garden.getHeight() ; row++ ){
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100d / garden.getHeight());
            rc.setVgrow(Priority.NEVER);
            gardenGrid.getRowConstraints().add(rc);
        }
        for (int col = 0 ; col < garden.getWidth(); col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100d / garden.getWidth());
            cc.setHgrow(Priority.NEVER);
            gardenGrid.getColumnConstraints().add(cc);
        }

        GardenCell[][] cells = gardenMapDAO.getGardenCells(garden);

        for (int x = 0; x < cells.length; x++)
        {
            for (int y = 0; y < cells[0].length; y++)
            {
                GardenCell cell = cells[x][y];
                if (cell != null) {

                    Button plotButton = createPlotButton(cell);
                    StackPane stack = new StackPane(plotButton);
                    gardenGrid.add(stack, x, y);
                }
            }
        }
    }

    /**
     * Creates a garden cell button for the garden grid
     * @param cell The cell to be turned into a button
     * @return A button representing the garden cell
     */
    private Button createPlotButton(GardenCell cell)
    {
        Button plotButton = new Button(cell.getPlant());
        String colour_s = colorToString(cell.getColour());
        plotButton.setStyle("-fx-background-color:" + colour_s);
        plotButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        plotButton.setMinSize(50, 50);
        plotButton.getStyleClass().add("garden-cell");
        plotButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                displayCell(cell);
            }
        });
        return plotButton;
    }

    /**
     * Converts a color into a string recognised by java
     * @param color The colour to be converted into a string
     * @return
     */
    private static String colorToString(Color color) {
        String colour_s = color.toString();
        colour_s = colour_s.substring(2, colour_s.length());
        colour_s = "#" + colour_s;
        return colour_s;
    }

    /**
     * Synchronises the garden details
     */
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

    /**
     * Method to change the garden name based on detecting the enter key
     */
    @FXML
    private void changeGardenName(){
        gardenNameText.setOnKeyPressed((event -> {
            if (event.getCode() == KeyCode.ENTER){
                confirmChangeGardenName();
            }
        }));
    }

    /**
     * Changes the garden width and height
     */
    @FXML
    private void changeGardenSize(){
        gardenWidth.setOnKeyPressed((event -> {
            if (event.getCode() == KeyCode.ENTER){
                confirmChangeGardenSize();
            }
        }));
        gardenHeight.setOnKeyPressed((event -> {
            if (event.getCode() == KeyCode.ENTER){
                confirmChangeGardenSize();
            }
        }));

    }

    /**
     * Confirm popup for changing the garden size
     */
    private void confirmChangeGardenSize() {
        String width = gardenWidth.getText().trim();
        String height = gardenHeight.getText().trim();
        int newWidth =  width.matches("^[1-9]\\d*$") ? Integer.parseInt(width) : -1;
        int newHeight = height.matches("^[1-9]\\d*$") ? Integer.parseInt(height) : -1;
        if (newHeight == garden.getHeight() && newWidth == garden.getWidth()){
            Popup.displayErrorPopup("Please enter in a different size!");
        }
        else if ( (newHeight <= 0 || newHeight >50 )|| (newWidth <=0 || newWidth >50) ){
            Popup.displayErrorPopup("Invalid size or input!");
        }
        else if (Popup.displayConfirmPopup("Are you sure you want to resize the garden to: " + width + "," + height)){
            gardenMapDAO.resizeMap(garden,newWidth,newHeight);
            syncGarden();
        }
        else{
            Popup.displayErrorPopup("Garden resizing cancelled");
        }
    }

    /**
     * Confirm popup for changing the garden name
     */
    private void confirmChangeGardenName(){
        String newName =  gardenNameText.getText();
        if (newName.equals(garden.getGardenName())){
            Popup.displayErrorPopup("Please enter in a different name!");
        }
        else if (Popup.displayConfirmPopup("Are you sure you want to change your garden name to: " + newName + "?")){
            garden.setGardenName(newName);
            gardenDAO.updateGarden(garden);
            syncGardenDetails();
        }
        else{
            Popup.displayErrorPopup("Garden name change has been cancelled.");
        }
    }

    /**
     * Displays the cell on the garden grid
     * @param cell The cell to be displayed
     */
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

    /**
     * Update the old cell to the new cell
     * @param oldCell Previous cell that needs to be updated
     * @param newCell New cell to replace the old cell
     */
    private void updateCell(GardenCell oldCell, GardenCell newCell)
    {
        gardenMapDAO.updateCell(newCell);
        Button plotButton = createPlotButton(newCell);
        gardenGrid.getChildren().remove(oldCell);
        gardenGrid.add(plotButton, newCell.getX(), newCell.getY());
    }

}
