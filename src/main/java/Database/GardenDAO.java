package Database;

import People.Garden;
import com.example.gardenplanner.UserSession;

import javax.xml.transform.Result;
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
        String query = "INSERT INTO Gardens (garden_owner, garden_name) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, garden.getOwnerId());
            stmt.setString(2, garden.getGardenName());
            stmt.executeUpdate();
            System.out.println("Garden added successfully.");

            ResultSet rs = stmt.getGeneratedKeys(); // get auto incremented keys
            addToGardenUsers(rs.getInt(1)); // update garden users table
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
                garden = new Garden(rs.getInt("garden_id"), rs.getInt("garden_owner"), rs.getString("garden_name"));
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
                Garden garden = new Garden(rs.getInt("garden_id"), rs.getInt("garden_owner"), rs.getString("garden_name"));
                gardens.add(garden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gardens;
    }

    @Override
    public void updateGarden(Garden garden) {
        String query = "UPDATE Gardens SET garden_name = ? WHERE garden_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, garden.getGardenName());
            stmt.setInt(2, garden.getGardenId());
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

    public void addToGardenUsers(int gardenID){
        String queryGardenUsers = "INSERT INTO Garden_Users (garden_id, user_id, access_level) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(queryGardenUsers)) {
            stmt.setInt(1, gardenID);
            stmt.setInt(2, UserSession.getInstance().getPersonId()); // current user session
            stmt.setString(3, "MANAGER");  // Users adding a garden are automatically set to Manager
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
