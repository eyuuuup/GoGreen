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
            System.out.println("addAction called");
            PreparedStatement state =
                    con.prepareStatement("SELECT action_id, parent_category "
                            + "FROM actions WHERE actions.action_name = ?");
            state.setString(1, action.getAction());
            ResultSet rs = state.executeQuery();

            int actionId = 0;
            int parentCategory = 0;
            while(rs.next()){
                actionId = rs.getInt(1);
                parentCategory = rs.getInt(2);
                System.out.println("parentcategpry: " + parentCategory);
                System.out.println("actionId: " + actionId);
            }

            PreparedStatement state1 =
                    con.prepareStatement("INSERT INTO events (action_id, date_time, points, token, parent_category)"
                    + "VALUES (?, ?, ?, ?, ?);");
            state1.setInt(1, actionId);
            state1.setString(
                    2,
                    new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(Calendar.getInstance().getTime()));
            state1.setInt(3, action.getValue());
            state1.setString(4, action.getUser());
            state1.setInt(5, parentCategory);
            state1.executeUpdate();
            System.out.println("INSERT success");
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static String retract(String token) {
        try {
            Connection con = DriverManager.getConnection();
            System.out.println("retract called");
            PreparedStatement state =
                    con.prepareStatement("select action_name, date_time, events.parent_category " +
                            "FROM actions, events WHERE actions.action_id = events.action_id \n" +
                            "AND events.parent_category = 1 AND events.token = ? ORDER BY date_time DESC LIMIT 3");
            state.setString(1,token);
            ResultSet rs = state.executeQuery();

            StringBuilder result = new StringBuilder();
            while(rs.next()){
                result.append(rs.getString(1) + " ");
                result.append(rs.getString(2) + "\n");
            }


            System.out.println("retract success");
            con.close();
            return result.toString();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "broke";
        }
    }



    public static void updateTotalScores(String token, int score){
        try{
            Connection con = DriverManager.getConnection();
            System.out.println("updateTotalScores called");


            PreparedStatement state =
                    con.prepareStatement("SELECT total_score "
                            + "FROM total_score WHERE total_score.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            int currentTotalScore = 0;
            while(rs.next()){
                currentTotalScore = rs.getInt(1);
                System.out.println("currentTotalScore: " + currentTotalScore);
            }

            currentTotalScore = currentTotalScore + score;
            PreparedStatement state1 =
                    con.prepareStatement("UPDATE total_score SET total_score = ? WHERE token = ?");
            state1.setInt(1, currentTotalScore);
            state1.setString(2,token);
            state1.executeUpdate();
            System.out.println("UPDATE success");
            con.close();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
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
            System.out.println("silentLogicCheck called");
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
            System.out.println("register called");
            PreparedStatement state =
                    con.prepareStatement("INSERT INTO "
                            + "user_data (token, username, hashpassword, mail)"
                            + "VALUES (?, ?, ?, ?);");
            state.setString(1, token);
            state.setString(2, user.getName());
            state.setString(3, user.getPassword());
            state.setString(4, tempMail);
            state.executeUpdate();

            PreparedStatement state1 =
                    con.prepareStatement("INSERT INTO "
                            + "total_score (total_score, token)"
                            + "VALUES (?, ?);");

            state1.setInt(1, 0);
            state1.setString(2,token);
            state1.executeUpdate();

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
            System.out.println("checkUsername called");
            PreparedStatement state =
                    con.prepareStatement("SELECT * "
                            + "FROM user_data WHERE user_data.username =  ? ");
            state.setString(1, username);

            ResultSet rs = state.executeQuery();


            String result = "";
            while (rs.next()) {
                result = rs.getString(2);
                System.out.println("Username: " + rs.getString(2));

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
        System.out.println("checkLogin called");
        if (checkUsername(user.getName())) {

            try {
                Connection con = DriverManager.getConnection();
                System.out.println("checkLogin called");
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
