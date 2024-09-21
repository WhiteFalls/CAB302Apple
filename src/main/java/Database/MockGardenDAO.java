package Database;

import People.Garden;

import java.sql.*;
import java.util.List;

import People.IPerson;
import com.example.gardenplanner.UserSession;

public class MockGardenDAO implements IGardenDAO{
    private final Connection connection;

    public MockGardenDAO(){
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void addGarden(Garden garden) {
        String queryGarden = "INSERT INTO Gardens (garden_owner, garden_name) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(queryGarden, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, UserSession.getInstance().getPersonId()); // current user session
            stmt.setString(2, UserSession.getInstance().getFirstName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys(); // Keys from auto increment
            addToGardenUsers(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Garden getGardenById(int gardenId) {
        return null;
    }

    @Override
    public List<Garden> getGardensByUserId(int userId) {
        return List.of();
    }

    @Override
    public void updateGarden(Garden garden) {

    }

    @Override
    public void deleteGarden(int gardenId) {

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
