package database;

import server.Action;
import server.TokenResponse;
import server.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Database is a class that will be used in communication with the server.
 */
public class Database {

    /**
     * This method saves the Action object in the database.
     * @param action An object of the class Action.
     * @return if the query succeeded.
     */
    public static boolean addAction(Action action) {
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT action_id "
                            + "FROM actions WHERE actions.action_name = ?");
            state.setString(1, action.getAction());
            ResultSet rs = state.executeQuery();

            int actionId = 0;
            while(rs.next()){
                actionId = rs.getInt(1);
                System.out.println("actionId: " + actionId);
            }

            PreparedStatement state1 =
                    con.prepareStatement("INSERT INTO events (action_id, date_time, points, token)"
                    + "VALUES (?, ?, ?, ?);");
            state1.setInt(1, actionId);
            state1.setString(
                    2,
                    new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(Calendar.getInstance().getTime()));
            state1.setInt(3, action.getValue());
            state1.setString(4, action.getUser());
            state1.executeUpdate();
            System.out.println("INSERT success");
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * This method queries the database with a token to look if the user exists.
     * @param token A string that contains the token.
     * @return if the query succeeded.
     */
    public static boolean silentLoginCheck(String token) {

        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT * FROM user_data WHERE user_data.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {

                System.out.println("token: " + rs.getString(1));
                String tokenResult = rs.getString(1);
                if(tokenResult.equals(token)){
                    System.out.println("Token exists");
                    return true;
                }
            }
            con.close();
            return false;


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    /**
     * This method registers a new user in the database.
     * @param user An User object.
     * @param token A String with the token.
     */
    public static void register(User user, String token) {
        String tempMail = "yuhyeet@gmail.com";
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("INSERT INTO "
                            + "user_data (token, username, hashpassword, mail)"
                            + "VALUES (?, ?, ?, ?);");
            state.setString(1, token);
            state.setString(2, user.getName());
            state.setString(3, user.getPassword());
            state.setString(4, tempMail);
            state.executeUpdate();
            System.out.println("INSERT success");
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    /**
     * This method checks if the username exists in the database.
     * @param username A string with the username.
     * @return if the users exists or not.
     */
    public static boolean checkUsername(String username) {

        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT * "
                            + "FROM user_data WHERE user_data.username =  ? ");
            state.setString(1, username);

            ResultSet rs = state.executeQuery();


            String result = "";
            while (rs.next()) {
                result = rs.getString(1);
                System.out.println("Username: " + rs.getString(1));

            }
            con.close();
            return result.equals(username);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * This method checks if the user has a token in the database.
     * @param user A user Object.
     * @return A TokenResponse object
     */
    public static TokenResponse checkLogin(User user) {
        if (checkUsername(user.getName())) {
            try {
                Connection con = DriverManager.getConnection();
                PreparedStatement state =
                        con.prepareStatement("SELECT * "
                                + "FROM user_data WHERE user_data.username =  ? ");
                state.setString(1, user.getName());
                ResultSet rs = state.executeQuery();

                String token = "";
                while(rs.next()){
                    token = rs.getString(1);
                }

                con.close();
                return new TokenResponse(token, true);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return new TokenResponse("null", false);
            }
        } else {
            return new TokenResponse("null", false);
        }

    }

}
