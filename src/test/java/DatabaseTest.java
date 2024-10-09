import Database.DatabaseConnection;
import Database.DatabaseConnection.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class DatabaseTest {
    @Test
    public void testConnection() {
        Connection conn = DatabaseConnection.getConnection();
        assertEquals(true, conn != null);
    }
}