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
    private IGardenDAO gardenDAO;

    public GardenMapDAO()
    {
        connection = DatabaseConnection.getConnection();
        gardenDAO = new GardenDAO();
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
    public void resizeMap(Garden garden, int newWidth, int newHeight) {
        GardenCell[][] newGrid = new GardenCell[newWidth][newHeight];
        GardenCell[][] currentGrid = getGardenCells(garden);
        for (int i = 0; i < Math.min(newWidth,garden.getWidth()); i++){
            for (int j = 0; j < Math.min(newHeight,garden.getHeight()); j++){
                newGrid[i][j] = currentGrid[i][j]; // overlap between old size and new size
            }
        }

        // fill in added space if needed
        for (int i = 0; i < newWidth; i++){
            for (int j = 0; j < newHeight; j++){
                if(newGrid[i][j] == null){
                newGrid[i][j] = makeDefaultCell(i,j); // not really needed tbf
                addDefaultCellsToGardenMap(garden,i,j);
                }
            }
        }
        removeOutOfBoundsCell(garden,newWidth,newHeight); // updates garden map
        updateGardenSize(garden,newWidth,newHeight);
    }

    private void updateGardenSize(Garden garden, int newWidth, int newHeight) {
        garden.setWidth(newWidth);
        garden.setHeight(newHeight);
        gardenDAO.updateGarden(garden);
    }


    private void removeOutOfBoundsCell(Garden garden, int newWidth, int newHeight){
        for (int i = 0; i < garden.getWidth(); i++) { // Cells to the right of the new width
            for (int j = 0; j < garden.getHeight(); j++) {
                if( i >= newHeight || j >= newWidth) {
                    removeCellsFromGardenMap(garden, i, j);
                }
            }
        }
    }

    private GardenCell makeDefaultCell(int x, int y){
        return new GardenCell("Empty",x,y,LocalDate.now(),null,Color.PERU);
    }

    private void addDefaultCellsToGardenMap(Garden garden,int x, int y) {
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
            System.out.println("Coords added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void removeCellsFromGardenMap(Garden garden,int x, int y) {
        String query = "DELETE FROM Garden_Map WHERE garden_id = ? AND x = ? and y = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, garden.getGardenId());
            stmt.setInt(2, x);
            stmt.setInt(3, y);
            stmt.executeUpdate();
            System.out.println("Coords removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
