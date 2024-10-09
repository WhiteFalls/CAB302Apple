package Database;

import GardenCell.Garden;
import Tasks.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GardenDAO implements IGardenDAO {

    private Connection connection;

    /**
     * Constructs a new GardenDAO and initializes the connection to the database
     */
    public GardenDAO() {
        connection = DatabaseConnection.getConnection();  // Get a connection from your utility
    }

    @Override
    public void addGarden(Garden garden) {
        String query = "INSERT INTO Gardens (garden_owner, garden_name, width, height) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, garden.getOwnerId());
            stmt.setString(2, garden.getGardenName());
            stmt.setInt(3, garden.getWidth());
            stmt.setInt(4, garden.getHeight());
            stmt.executeUpdate();
            System.out.println("Garden added successfully.");

            ResultSet rs = stmt.getGeneratedKeys(); // get auto incremented keys
            garden.setGardenId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Garden getGardenById(int gardenId) {
        String query = "SELECT * FROM Gardens WHERE garden_id = ?";
        Garden garden = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gardenId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                garden = new Garden(rs.getInt("garden_id"),
                        rs.getString("garden_name"),
                        rs.getInt("garden_owner"),
                        rs.getInt("width"),
                        rs.getInt("height"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return garden;
    }

    @Override
    public List<Garden> getGardensByUserId(int userId) {
        String query = "SELECT * FROM Gardens WHERE garden_owner = ?";
        List<Garden> gardens = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Garden garden = new Garden(rs.getInt("garden_id"),
                        rs.getString("garden_name"),
                        rs.getInt("garden_owner"),
                        rs.getInt("width"),
                        rs.getInt("height"));
                gardens.add(garden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gardens;
    }


    @Override
    public Garden getGardenByUserId(int userId) {
        String query = "SELECT * FROM Gardens WHERE garden_owner = ?";
        Garden garden = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                garden = new Garden(rs.getInt("garden_id"),
                        rs.getString("garden_name"),
                        rs.getInt("garden_owner"),
                        rs.getInt("width"),
                        rs.getInt("height"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return garden;
    }

    @Override
    public void updateGarden(Garden garden) {
        String query = "UPDATE Gardens SET garden_name = ?, width = ?, height = ? WHERE garden_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, garden.getGardenName());
            stmt.setInt(2, garden.getWidth());
            stmt.setInt(3, garden.getHeight());
            stmt.setInt(4, garden.getGardenId());
            stmt.executeUpdate();
            System.out.println("Garden updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGarden(int gardenId) {
        String query = "DELETE FROM Gardens WHERE garden_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gardenId);
            stmt.executeUpdate();
            System.out.println("Garden deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Garden getGardenByTaskId(Task task)
    {
        String query = "SELECT * FROM Tasks WHERE task_id = ?";
        Garden garden = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, task.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                garden = getGardenById(rs.getInt("garden_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return garden;
    }
}
