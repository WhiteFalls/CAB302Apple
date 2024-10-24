package com.gardenapplication.controller;

import Database.DatabaseConnection;
import Database.PersonDAO;
import Util.Popup;
import com.gardenapplication.Gary;
import com.gardenapplication.UserSession;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import People.IPerson;

public class NavbarController {

    @FXML
    public Button homeIconButton;

    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void goToHome() throws IOException {
        loadPage("main-page.fxml");
    }

    @FXML
    private void goToGarden() throws IOException {
        if (findNumGardensOwned() == 1){
            loadPage("garden-view.fxml");
        }
        else {
            Popup.displayErrorPopup("You must create a garden first!");
        }
    }

    @FXML
    private void goToManagement() throws IOException {
        if (findNumGardensOwned() == 1){
            loadPage("garden-management-view.fxml");
        }
        else {
            Popup.displayErrorPopup("You must create a garden first!");
        }
    }

    @FXML
    private void goToTodoList() throws IOException {
        loadPage("usertodoList.fxml");
    }

    @FXML
    private void goToPlantSearch() throws IOException {
        loadPage("plant-info-page.fxml");
    }

    private void loadPage(String fxml) throws IOException {
        stage = (Stage) homeIconButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Gary.class.getResource(fxml));
        Scene scene = new Scene(loader.load(), 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    private int findNumGardensOwned(){
        Connection connection = DatabaseConnection.getConnection();
        PersonDAO personDAO = new PersonDAO();
        int personId = UserSession.getInstance().getPersonId();
        IPerson loggedInUser = personDAO.getPerson(personId);

        String query = "SELECT COUNT(*) FROM Gardens WHERE garden_owner = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, loggedInUser.getUserId());
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1);
        }catch(SQLException e ) {
            e.printStackTrace();
        }
        return -1; // handle this case
    }
}
