package com.example.gardenplanner.controller;

import Database.PlantDAO;
import GardenCell.Plant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

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

    @FXML
    public void handleSearch() {
        String searchQuery = PlantSearchBar.getText();
        searchPlants(searchQuery);
    }

    private ObservableList<Plant> plantsObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        plant_id.setCellValueFactory(new PropertyValueFactory<>("plantID"));
        plant_name.setCellValueFactory(new PropertyValueFactory<>("plantName"));
        plant_description.setCellValueFactory(new PropertyValueFactory<>("plantDescription"));
        watering.setCellValueFactory(new PropertyValueFactory<>("watering"));
        optimal_sunlight.setCellValueFactory(new PropertyValueFactory<>("optimalSunlight"));

    }

    public void searchPlants(String searchQuery) {
        PlantDAO plantDAO = new PlantDAO();
        ArrayList<Plant> plants = plantDAO.getPlantContainsName(searchQuery);
        plantsObservableList.setAll(plants);
        plantTableView.setItems(plantsObservableList);
    }
}

