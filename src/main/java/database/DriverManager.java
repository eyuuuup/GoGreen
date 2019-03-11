package database;

import java.sql.Connection;
import java.sql.SQLException;

public class DriverManager   {

    public static Connection getConnection() throws SQLException {

        Connection conn = java.sql.DriverManager.getConnection("jdbc:postgresql://ec2-54-75-232-114.eu-west-1.compute.amazonaws.com:5432/d763drd7k09df2",
                "sdglqfxhmgoxdy", "86bf9899b7ba509490d36a1735109060270adefe7db96bdfc2b59c959b069e3d");
       
        System.out.println("Connected to database");
        return conn;
    }
}
