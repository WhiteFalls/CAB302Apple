package Database;

import People.Garden;
import People.IPerson;
import People.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GardenUsersDAO implements IGardenUsersDAO {
    private final Connection connection;

    // Constructor to initialize the database connection
    public GardenUsersDAO(Connection connection) {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public List<Integer> getPeopleIdsInGarden(Garden garden) {
        String query = "SELECT * FROM Garden_Users WHERE garden_id = ?";
        List<Integer> peopleIds = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, garden.getGardenId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                if (id == 0)
                {
                    break;
                }
                peopleIds.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peopleIds;
    }

    @Override
    public void addPersonToGarden(IPerson person, Garden garden) {
        String query = "INSERT INTO Garden_Users (garden_id, user_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, garden.getGardenId());
            stmt.setInt(2, person.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePersonFromGarden(IPerson person, Garden garden) {
        String query = "DELETE FROM Garden_Users WHERE user_id = ? AND garden_id = ? VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person.getUserId());
            stmt.setInt(2, garden.getGardenId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

