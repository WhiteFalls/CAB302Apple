package Database;

import Database.DatabaseConnection;
import Database.IPlantDAO;
import GardenCell.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class PlantDAO implements IPlantDAO {

        private Connection connection;


        public PlantDAO() {
            connection = DatabaseConnection.getConnection();  // Get a connection from your utility
        }

        @Override
        public Plant getPlantById(int plantId) {
            String query = "SELECT * FROM Plants WHERE plant_id = ?";
            Plant plant = null;

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, plantId);
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

    }


