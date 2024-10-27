package com.gardenapplication.controller;

import Database.PlantDAO;
import GardenCell.Plant;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlantSearchController implements Initializable {

    @FXML
    private TableView<Plant> plantTableView;

    @FXML
    private TableColumn<Plant, Integer> plant_id;

    @FXML
    private TableColumn<Plant, String> plant_name;

    @FXML
    private TableColumn<Plant, String> plant_description;

    @FXML
    private TableColumn<Plant, String> watering;

    @FXML
    private TableColumn<Plant, String> optimal_sunlight;

    @FXML
    private TextField PlantSearchBar;

    private PlantDAO plantDAO;

    public PlantSearchController()
    {
        plantDAO = new PlantDAO();
    }

    /**
     * Retrieves the search query
     */
    @FXML
    public void handleSearch() {
        String searchQuery = PlantSearchBar.getText();
        searchPlants(searchQuery);
    }

    /**
     * Initializes the plant search controller
     * @param url The resource to be located
     * @param resourceBundle The localised data
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        plant_id.setCellValueFactory(new PropertyValueFactory<>("plantID"));
        plant_name.setCellValueFactory(new PropertyValueFactory<>("plantName"));

        plant_description.setCellValueFactory(new PropertyValueFactory<>("plantDescription"));
        wrapCellText(plant_description);

        watering.setCellValueFactory(new PropertyValueFactory<>("watering"));
        wrapCellText(watering);

        optimal_sunlight.setCellValueFactory(new PropertyValueFactory<>("optimalSunlight"));
        wrapCellText(optimal_sunlight);

        ArrayList<Plant> plant_list = plantDAO.getAllPlants();
        plantTableView.getItems().addAll(plant_list);
    }

    /**
     * Wraps the text in the column
     * @param column The column for the text to be wrapped
     */
    private void wrapCellText(TableColumn<Plant, String> column)
    {
        column.setCellFactory(new Callback<TableColumn<Plant, String>, TableCell<Plant, String>>() {
            @Override
            public TableCell<Plant, String> call(TableColumn<Plant, String> param) {
                return new TableCell<Plant, String>() {
                    private final Text text = new Text();

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            text.setText(item);
                            text.setWrappingWidth(getTableColumn().getWidth());  // Wrap to column width
                            setGraphic(text);  // Set the text as the graphic in the cell
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    /**
     * Searches the plant and displays them in the tableview
     * @param searchQuery The plant to be queried
     */
    public void searchPlants(String searchQuery) {
        ArrayList<Plant> plants = plantDAO.getPlantContainsName(searchQuery);
        plantTableView.getItems().clear();
        plantTableView.getItems().addAll(plants);
    }
}


