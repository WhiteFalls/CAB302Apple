package Database;

import GardenCell.GardenCell;
import People.Garden;
import com.example.gardenplanner.controller.GardenManagementController;
import org.apache.commons.lang.ObjectUtils;

import java.sql.*;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.util.HexFormat;

public class GardenMapDAO implements IGardenMapDAO{
    private Connection connection;

    public GardenMapDAO()
    {
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public void createDefaultMap(Garden garden) {
        for (int x = 0; x < garden.getWidth(); x++)
        {
            for(int y = 0; y < garden.getHeight(); y++)
            {
                String query = "INSERT INTO Garden_Map (garden_id, x, y, plant_name, planted_date, harvest_date, colour) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setInt(1, garden.getGardenId());
                    stmt.setInt(2, x);
                    stmt.setInt(3, y);
                    stmt.setString(4, "Empty");
                    stmt.setDate(5, Date.valueOf(LocalDate.now()));
                    stmt.setDate(6, null);
                    stmt.setString(7, Color.PERU.toString());
                    stmt.executeUpdate();
                    System.out.println("Garden added successfully.");


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public GardenCell[][] getGardenCells(Garden garden) {
        GardenCell[][] plot = new GardenCell[garden.getWidth()][garden.getHeight()];

        String query = "SELECT * FROM Garden_Map WHERE garden_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, garden.getGardenId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int x = rs.getInt("x");
                int y = rs.getInt("y");

                LocalDate harvestDate;
                //String color =Integer.toHexString(rs.getInt("colour"));
                try
                {
                    harvestDate = rs.getDate("harvest_data").toLocalDate();
                }
                catch (SQLException e)
                {
                    harvestDate = null;
                }
                plot[x][y] = new GardenCell(rs.getString("plant_name"),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getDate("planted_date").toLocalDate(),
                        harvestDate,
                        Color.web(rs.getString("colour")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plot;
    }

    @Override
    public void updateCell(GardenCell cell) {
        String query = "UPDATE Garden_Map SET plant_name = ?, planted_date = ?, harvest_date = ?, colour = ? WHERE x = ? AND y = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cell.getPlant());
            stmt.setDate(2, Date.valueOf(cell.getPlantedDate()));
            if (cell.getHarvestDate() == null)
            {
                stmt.setDate(3, null);
            }
            else {
                stmt.setDate(3, Date.valueOf(cell.getHarvestDate()));
            }
            stmt.setString(4, cell.getColour().toString());  // Update category as text
            stmt.setInt(5, cell.getX());
            stmt.setInt(6, cell.getY());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resizeMap(Garden garden) {

    }
}
