package Database;

import GardenCell.Plant;
import javafx.beans.Observable;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import org.apache.commons.beanutils.converters.CharacterArrayConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlantDAO implements IPlantDAO {

        private Connection connection;


        public PlantDAO() {
            connection = DatabaseConnection.getConnection();  // Get a connection from your utility
        }

        @Override
        public Plant getPlantById(Plant plant) {
            String query = "SELECT * FROM Plants WHERE plant_id = ?";
            Plant plant = null;

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, plant.getPlantID());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    plant = new Plant(rs.getInt("plant_id"),
                            rs.getString("plant_name"),
                            rs.getString("plant_description"),
                            rs.getString("watering"),
                            rs.getString("optimal_sunlight"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return plant;
        }

        public ArrayList<Plant> getPlantContainsName(String userInput)
        {
            ArrayList<Plant> plants = new ArrayList<Plant>();
            String query = "SELECT * FROM Plants WHERE plant_name LIKE ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, "%" + userInput + "%");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Plant plant = new Plant(rs.getInt("plant_id"),
                            rs.getString("plant_name"),
                            rs.getString("plant_description"),
                            rs.getString("watering"),
                            rs.getString("optimal_sunlight"));
                    if (plant.getPlantID() == 0)
                    {
                        break;
                    }
                    plants.add(plant);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return plants;
        }

        @Override
        public Plant getPlantById(int plantId) {
            return null;
        }
    }



