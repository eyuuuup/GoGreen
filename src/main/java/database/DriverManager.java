package database;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * DriverManager is a class that sets up the connection to the database.
 */
public class DriverManager {

    /**
     * This method sets up the actual connect with the information provided.
     *
     * @return A Connection object.
     */
    public static Connection getConnection() throws SQLException {
        Connection con = java.sql.DriverManager.getConnection(
                "jdbc:postgresql://ec2-54-75-232-114.eu-west-1.compute.amazonaws.com:"
                        + "5432/d763drd7k09df2?user=sdglqfxhmgoxdy"
                        + "&password=86bf9899b7ba509490d36a1735109060270"
                        + "adefe7db96bdfc2b59c959b069e3d"
                        + "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        return con;
    }
}
