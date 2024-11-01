package Database;

import GardenCell.Garden;
import People.IPerson;

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

    /**
     * Retrieves all users that are in the garden
     * @param garden The garden to get the user IDs from
     * @return A list of user IDs that belong in the garden
     */
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

    /**
     * Adds a person to the garden
     * @param person The person to be added
     * @param garden The garden to add that person to
     * @param Role The person's particular role in the garden
     */
    @Override
    public void addPersonToGarden(IPerson person, Garden garden, String Role) {
        String query = "INSERT INTO Garden_Users (garden_id, user_id, access_level) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, garden.getGardenId());
            stmt.setInt(2, person.getUserId());
            stmt.setString(3, Role);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a person from the garden
     * @param person The person to be removed
     * @param garden The garden to remove the person from
     */
    @Override
    public void removePersonFromGarden(IPerson person, Garden garden) {
        String query = "DELETE FROM Garden_Users WHERE user_id = ? AND garden_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person.getUserId());
            stmt.setInt(2, garden.getGardenId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all gardens the user belongs to
     * @param userId The user ID of the specified person
     * @return A list of gardens the user is in
     */
    @Override
    public List<Garden> getAllGardenByUserId(int userId) {

        // find where garden ids matches for users and garden table
        String query = "SELECT Gardens.*, Garden_Users.user_id, Garden_Users.garden_id " +
                "FROM Gardens " +
                "INNER JOIN Garden_Users ON Gardens.garden_id = Garden_Users.garden_id " +
                "WHERE Garden_Users.user_id = ?";
        List<Garden> usergardenList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Garden garden = new Garden(rs.getInt("garden_id"),
                        rs.getString("garden_name"),
                        rs.getInt("garden_owner"),
                        rs.getInt("width"),
                        rs.getInt("height"));
                usergardenList.add(garden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usergardenList;
    }
}

