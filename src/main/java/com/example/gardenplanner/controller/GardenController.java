package com.example.gardenplanner.controller;

import Database.*;
import GardenCell.GardenCell;
import People.Garden;
import People.IPerson;
import com.example.gardenplanner.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

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

    private IPersonDAO personDAO;
    private GardenDAO gardenDAO;
    private GardenMapDAO gardenMapDAO;
    private GardenUsersDAO gardenUsersDAO;
    private Garden garden;
    private Connection connection;


    public GardenController()
    {
        personDAO = new PersonDAO();
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
        gardenGrid.getRowConstraints().clear(); //idk but it works
        gardenGrid.getColumnConstraints().clear();
        gardenGrid.getChildren().clear();
        gardenTitle.setText(garden.getGardenName());
        syncGardenDetails();

        for (int row = 0 ; row < garden.getHeight() ; row++ ){
            RowConstraints rc = new RowConstraints();
            //rc.setFillHeight(true);
            rc.setPercentHeight(100d / garden.getHeight());
            rc.setVgrow(Priority.NEVER);
            gardenGrid.getRowConstraints().add(rc);
        }
        for (int col = 0 ; col < garden.getWidth(); col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            //cc.setFillWidth(true);
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
                if (cell != null) { // added in cause it was telling me cells was null

                    Button plotButton = createPlotButton(cell);
                    StackPane stack = new StackPane(plotButton);

//                    GridPane.setFillWidth(plotButton, true);
//                    GridPane.setFillHeight(plotButton, true);

                    gardenGrid.add(stack, x, y); // it actually takes in y,x but it think we swapped the coords everywhere so it cancels out technically
                    //gardenGrid.setMargin(plotButton, new Insets(1));
                }
            }
        }
    }

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
    @FXML
    private void changeGardenName(){
        gardenNameText.setOnKeyPressed((event -> {
            if (event.getCode() == KeyCode.ENTER){
                confirmChangeGardenName();
            }
        }));
    }

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

    private void confirmChangeGardenSize() {
        int newWidth = Integer.parseInt(gardenWidth.getText());
        int newHeight = Integer.parseInt(gardenHeight.getText());
        if (newHeight == garden.getHeight() && newWidth == garden.getWidth()){
            displayPopup("Please enter in a different size!");
        }
        else if (newHeight <= 0 || newWidth <=0 ){
            displayPopup("Invalid size!");
        }
        else if (displayConfirmPopup("Are you sure you want to resize the garden to: " + gardenWidth.getText() + "," + gardenHeight.getText())){
            gardenMapDAO.resizeMap(garden,newWidth,newHeight);
            syncGarden();
        }
        else{
            displayPopup("Garden resizing cancelled");
        }
    }

    private void confirmChangeGardenName(){
        String newName =  gardenNameText.getText();
        if (newName.equals(garden.getGardenName())){
            displayPopup("Please enter in a different name!");
        }
        else if (displayConfirmPopup("Are you sure you want to change your garden name to: " + newName + "?")){
            garden.setGardenName(newName);
            gardenDAO.updateGarden(garden);
            syncGardenDetails();
        }
        else{
            displayPopup("Garden name change has been cancelled.");
        }
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

    /**
     * Sets scene back to the main page of the application
     * @param event The event that triggers the page change
     */
    public void goBackToMainPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gardenplanner/main-page.fxml"));
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, 1200, 600);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
