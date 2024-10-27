package Database;

import GardenCell.Plant;

import java.sql.*;
import java.util.ArrayList;

public class PlantDAO implements IPlantDAO {

    private Connection connection;


    public PlantDAO() {
        connection = DatabaseConnection.getConnection();  // Get a connection from your utility
    }


    /**
     * Retrieves all plants that contain a string
     * @param userInput The word that the plants need to contain
     * @return An array of plants that contain the user input string
     */
    public ArrayList<Plant> getPlantContainsName(String userInput) {
        ArrayList<Plant> plants = new ArrayList<Plant>();
        String query = "SELECT * FROM garden_vegetables WHERE plant_name LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + userInput + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Plant plant = new Plant(rs.getInt("plant_id"),
                        rs.getString("plant_name"),
                        rs.getString("plant_description"),
                        rs.getString("watering"),
                        rs.getString("optimal_sun"));
                if (plant.getPlantID() == 0) {
                    break;
                }
                plants.add(plant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    /**
     * Retrieves all the plants from the database
     * @return All the plants from the database
     */
    public ArrayList<Plant> getAllPlants() {
        ArrayList<Plant> plants = new ArrayList<>();
        String query = "SELECT * FROM garden_vegetables";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Plant plant = new Plant(rs.getInt("plant_id"),
                        rs.getString("plant_name"),
                        rs.getString("plant_description"),
                        rs.getString("watering"),
                        rs.getString("optimal_sun"));
                if (plant.getPlantID() == 0) {
                    break;
                }
                plants.add(plant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

}



