package Database;

import GardenCell.Garden;

import java.sql.*;

public class GardenDAO implements IGardenDAO {

    private Connection connection;

    /**
     * Constructs a new GardenDAO and initializes the connection to the database
     */
    public GardenDAO() {
        connection = DatabaseConnection.getConnection();  // Get a connection from your utility
    }

    /**
     * Adds the garden to be database
     * @param garden The garden to be added
     */
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

    /**
     * Retrieves the garden based on the user ID
     * @param userId The ID of the user
     * @return The garden owned by the user
     */
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

    /**
     * Updates the garden
     * @param garden The garden to be updated
     */
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

    /**
     * Removes the garden from the database given the garden ID
     * @param gardenId The ID of the garden to be deleted
     */
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

}
