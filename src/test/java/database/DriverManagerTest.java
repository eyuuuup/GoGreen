package database;

import org.junit.Test;

import java.sql.SQLException;

public class DriverManagerTest {

    @Test
    public void getConnection() throws SQLException {
        database.DriverManager.getConnection();
    }
}