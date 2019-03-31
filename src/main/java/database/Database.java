package database;

import org.joda.time.Instant;
import server.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Database is a class that will be used in communication with the server.
 */
public class Database {

    /**
     * This method gets the username from the database.
     *
     * @param token A String with the token.
     * @return the username.
     */
    public static String getUsername(String token) {
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT username "
                            + "FROM user_data WHERE token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            String userName = "";
            while (rs.next()) {
                userName = rs.getString(1);
                System.out.println("username: " + userName);

            }
            con.close();
            return userName;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "no username found";
        }
    }

    /**
     * This methods queries the database for username,
     * mail and totalscore of user, found by token
     *
     * @param token
     * @return username, mail, totalscore of user
     */
    public static User getUser(String token) {
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT user_data.username, "
                            + "user_data.mail, total_score.total_score "
                            + "FROM user_data "
                            + "JOIN total_score ON user_data.username = total_score.username "
                            + "WHERE user_data.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            User user = new User();
            if (rs.next()) {
                user.setName(rs.getString(1));
                System.out.println("username: " + user.getName());
                user.setEmail(rs.getString(2));
                System.out.println("email: " + user.getEmail());
                user.setTotalScore(rs.getInt(3));
                System.out.println("totalScore: " + user.getTotalScore());
                con.close();
                return user;
            }
            con.close();
            return null;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * This method saves the Action object in the database.
     *
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

            while (rs.next()) {
                actionId = rs.getInt(1);
                parentCategory = rs.getInt(2);
            }

            if (parentCategory == 1) {
                long last = getLastMeal(action.getToken());
                long now = Instant.now().getMillis();

                long max = 12 * 60 * 60 * 1000;
                long min = 1000;
                System.out.println("now:" + now + "\tlast:" + last);
                if ((min < now - last) && (now - last < max)) {
                    return false;
                }
            }


            PreparedStatement state1 =
                    con.prepareStatement("INSERT INTO events (action_id, date_time, "
                            + "points, parent_category, username, carbon_reduced, carbon_produced)"
                            + "VALUES (?, ?, ?, ?, ?,?,?);");
            state1.setInt(1, actionId);

            Long outputDate = Instant.now().getMillis();

            state1.setLong(2, outputDate);
            state1.setInt(3, action.getValue());
            state1.setInt(4, parentCategory);
            state1.setString(5, getUsername(action.getToken()));
            state1.setDouble(6, action.getCarbonReduced());
            state1.setDouble(7, action.getCarbonProduced());
            state1.executeUpdate();

            
            updateTotalScores(action.getToken(), action.getValue(),
                    action.getCarbonReduced(), action.getCarbonProduced());
            

            System.out.println("INSERT success");
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * This method gets the history of a user.
     *
     * @param token the token from a user.
     * @return the history in a String.
     */
    public static ActionList retract(String token) {
        try {
            Connection con = DriverManager.getConnection();
            System.out.println("retract called");
            PreparedStatement state =
                    con.prepareStatement("SELECT actions.action_name, events.points, "
                            + "events.carbon_reduced, events.carbon_produced, events.date_time "
                            + "FROM events JOIN actions ON events.action_id = actions.action_id "
                            + "WHERE events.username = ? "
                            + "ORDER BY date_time DESC LIMIT 3");
            state.setString(1, getUsername(token));
            ResultSet rs = state.executeQuery();

            ArrayList<Action> list = new ArrayList<>();
            while (rs.next()) {
                Action action = new Action();

                action.setAction(rs.getString(1));
                action.setValue(rs.getInt(2));
                action.setCarbonProduced(rs.getInt(3));
                action.setCarbonReduced(rs.getInt(4));
                action.setDate(rs.getLong(5));

                list.add(action);
            }

            System.out.println("retract success");
            con.close();
            return new ActionList(list);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }


//    /**
//     * returns the carbon reduction.
//     * @param token token
//     * @return carbon reduction
//     */
//    public static int getCarbonReduced(String token) {
//        try {
//            Connection con = DriverManager.getConnection();
//            System.out.println("getCarbonReduced called");
//
//            PreparedStatement state =
//                    con.prepareStatement("SELECT carbon_reduced "
//                            + "FROM total_score JOIN user_data ON "
//                            + "total_score.username = user_data.username "
//                            + "WHERE user_data.token = ?");
//            state.setString(1, token);
//            ResultSet rs = state.executeQuery();
//
//            int currentCarbonReduced = 0;
//            while (rs.next()) {
//                currentCarbonReduced = rs.getInt(1);
//                System.out.println("carbon_reduced: " + currentCarbonReduced);
//            }
//
//            con.close();
//            return currentCarbonReduced;
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            return 0;
//        }
//    }
//
//    /**
//     * get carbon produced.
//     * @param token token
//     * @return carbon produced
//     */
//    public static int getCarbonProduced(String token) {
//        try {
//            Connection con = DriverManager.getConnection();
//            System.out.println("getCarbonProduced called");
//
//            PreparedStatement state =
//                    con.prepareStatement("SELECT carbon_produced "
//                            + "FROM total_score JOIN user_data ON "
//                            + "total_score.username = user_data.username "
//                            + "WHERE user_data.token = ?");
//            state.setString(1, token);
//            ResultSet rs = state.executeQuery();
//
//            int currentCarbonProduced = 0;
//            while (rs.next()) {
//                currentCarbonProduced = rs.getInt(1);
//                System.out.println("carbon_reduced: " + currentCarbonProduced);
//            }
//
//            con.close();
//            return currentCarbonProduced;
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            return 0;
//        }
//    }

    public static Action getCarbonValues(String token) {
        System.out.println("get carbon values called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT carbon_produced, carbon_reduced "
                            + "FROM total_score JOIN user_data ON total_score.username = user_data.username "
                            + "WHERE user_data.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            con.close();

            if (rs.next()) {
                Action a = new Action();
                a.setCarbonProduced(rs.getDouble(1));
                a.setCarbonReduced(rs.getDouble(2));
                System.out.println("carbon_produced: " + a.getCarbonProduced() + "\tcarbon_reduced: " + a.getCarbonReduced());

                return a;
            }
            return null;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * This method updates the total score of a user.
     *
     * @param token the token from a user.
     * @param score the score that should be added to the total.
     */
    public static void updateTotalScores(String token, int score, double carbonReduced, double carbonProduced) {
        try {
            System.out.println("updateTotalScores called");
            int currentTotalScore = getTotalScore(token);
            Action action = Database.getCarbonValues(token);
            double currentCarbonReduced = action.getCarbonReduced();
            double currentCarbonProduced = action.getCarbonProduced();
            currentTotalScore = currentTotalScore + score;
            currentCarbonReduced = currentCarbonReduced + carbonReduced;
            currentCarbonProduced = currentCarbonProduced + carbonProduced;

            Connection con = DriverManager.getConnection();
            PreparedStatement state1 =
                    con.prepareStatement("UPDATE total_score "
                            + "SET total_score = ?, carbon_reduced = ?, "
                            + "carbon_produced = ? WHERE username = ?");
            state1.setInt(1, currentTotalScore);
            state1.setString(4, getUsername(token));
            state1.setDouble(2, currentCarbonReduced);
            state1.setDouble(3, currentCarbonProduced);
            state1.executeUpdate();
            System.out.println("UPDATE success");
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * This method queries the database to get the total score of a user.
     *
     * @param token A String with the token of the user.
     * @return the total score of a user.
     */
    public static int getTotalScore(String token) {
        try {
            Connection con = DriverManager.getConnection();
            System.out.println("getTotalScore called");

            PreparedStatement state =
                    con.prepareStatement("SELECT total_score "
                            + "FROM total_score JOIN user_data ON "
                            + "total_score.username = user_data.username "
                            + "WHERE user_data.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            int currentTotalScore = 0;
            while (rs.next()) {
                currentTotalScore = rs.getInt(1);
                System.out.println("currentTotalScore: " + currentTotalScore);
            }

            con.close();
            return currentTotalScore;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * Get the total score for a given user.
     *
     * @param username the username
     * @return the total score
     */
    public static int getTotalScoreByUser(String username) {
        try {
            Connection con = DriverManager.getConnection();
            System.out.println("getTotalScore called");


            PreparedStatement state =
                    con.prepareStatement("SELECT total_score "
                            + "FROM total_score WHERE total_score.username = ?");
            state.setString(1, username);
            ResultSet rs = state.executeQuery();

            int currentTotalScore = 0;
            while (rs.next()) {
                currentTotalScore = rs.getInt(1);
                System.out.println("currentTotalScore: " + currentTotalScore);
            }

            con.close();
            return currentTotalScore;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * This method queries the database with a token to look if the user exists.
     *
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

            if (rs.next()) {
                con.close();
                System.out.println("token found");
                return true;
            }
            System.out.println("token not found");
            con.close();
            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    /**
     * This method registers a new user in the database.
     *
     * @param user  An User object.
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
                            + "total_score (total_score, username)"
                            + "VALUES (?, ?);");

            state1.setInt(1, 0);
            state1.setString(2, getUsername(token));
            state1.executeUpdate();

            System.out.println("INSERT success");
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    /**
     * This method checks if the username exists in the database.
     *
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
     *
     * @param user A user Object.
     * @return A TokenResponse object
     */
    public static TokenResponse checkLogin(User user) {
        System.out.println("checkLogin called");
        if (checkUsername(user.getName())) {

            try {
                Connection con = DriverManager.getConnection();
                PreparedStatement state =
                        con.prepareStatement("SELECT * "
                                + "FROM user_data WHERE user_data.username =  ? ");
                state.setString(1, user.getName());
                ResultSet rs = state.executeQuery();

                String token = "";
                while (rs.next()) {
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

    /**
     * This method adds User B as a friend of User A.
     *
     * @param friend A Friend object.
     * @return if the query succeeded.
     */
    public static boolean addFriend(CompareFriends friend) {
        System.out.println("addFriend called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("INSERT INTO friends (user_a, user_b) VALUES"
                            + "(?, ?) ");
            state.setString(1, getUsername(friend.getToken()));
            state.setString(2, friend.getUsername());
            state.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * This method shows the friends of a user.
     *
     * @param token A String of the token.
     * @return the FriendsList object of all friends of a user.
     */
    public static FriendsList showFriends(String token) {
        System.out.println("showFriends called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT user_b FROM friends WHERE user_a = ?");
            state.setString(1, getUsername(token));
            ResultSet rs = state.executeQuery();

            ArrayList<CompareFriends> result = new ArrayList<>();
            while (rs.next()) {
                String usernameFriend = rs.getString(1);
                int score = getTotalScoreByUser(usernameFriend);
                CompareFriends friend = new CompareFriends(usernameFriend, score);
                result.add(friend);
            }
            con.close();
            return new FriendsList(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * This methods shows the followers.
     *
     * @param token A String of the token.
     * @return FriendsList object with the followers.
     */
    public static FriendsList showFollowers(String token) {
        System.out.println("showFollowers called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "SELECT friends.user_a, total_score.total_score "
                            + "FROM friends "
                            + "JOIN total_score ON friends.user_a=total_score.username "
                            + "WHERE user_b = ?");
            state.setString(1, getUsername(token));
            ResultSet rs = state.executeQuery();

            ArrayList<CompareFriends> result = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString(1);
                int score = rs.getInt(2);
                result.add(new CompareFriends(username, score));
            }
            con.close();

            return new FriendsList(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * This methods gets the leaderboard.
     *
     * @return A FriendsList object with the leaderboard inside.
     */
    public static FriendsList getLeaderboard() {
        System.out.println("getLeaderboard called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "SELECT username, total_score "
                            + "FROM total_score "
                            + "ORDER BY total_score DESC LIMIT 10");
            ResultSet rs = state.executeQuery();

            ArrayList<CompareFriends> result = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString(1);
                int score = rs.getInt(2);
                result.add(new CompareFriends(username, score));
            }
            con.close();

            FriendsList friendList = new FriendsList(result);

            return friendList;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * This methods gets the last meal.
     *
     * @param token A String of the token of the user.
     * @return int with the time.
     */
    public static long getLastMeal(String token) {
        System.out.println("getLastMeal called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "SELECT date_time "
                            + "FROM events JOIN user_data ON "
                            + "events.username = user_data.username "
                            + "WHERE user_data.token = ? ORDER BY date_time DESC LIMIT 1");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            long time = 0;
            while (rs.next()) {
                time = rs.getLong(1);
            }
            con.close();

            return time;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     * (Experimental)
     * This methods checks if the user completed a One Time Event.
     *
     * @param token username of the user.
     * @param id    the id of the One Time Event.
     * @return a boolean.
     */
    public static boolean checkOneTimeEvent(String token, int id) {
        System.out.println("checkOneTimeEvent called");
        try {
            String username = getUsername(token);
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "SELECT action_id "
                            + "FROM events "
                            + "WHERE user_name = ? AND action_id = ?");
            state.setString(1, username);
            state.setInt(2, id);
            ResultSet rs = state.executeQuery();
            con.close();
            return rs.next();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static OnLoadValues oneTimeEvent(String token) {

        //CHECK THE ACTION IDS AND REPLACE WITH ONE METHOD
        return new OnLoadValues(Database.checkOneTimeEvent(token, 6), Database.checkOneTimeEvent(token, 7));

    }

    /**
     * This methods adds a challenge.
     *
     * @param usernameA username of a user.
     * @param usernameB username of a user.
     * @param goal      amount of points to win.
     */
    public static void addChallenge(String usernameA, String usernameB, int goal) {
        System.out.println("addChallenge called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "INSERT INTO "
                            + "challenges (goal, time_added, user_a, user_b, score_a, score_b)"
                            + "VALUES (?, ?, ?, ?, ( "
                            + "SELECT total_score FROM total_score WHERE username = ? "
                            + "), ( "
                            + "SELECT total_score FROM total_score WHERE username = ? "
                            + "));");

            state.setInt(1, goal);
            state.setLong(2, Instant.now().getMillis());
            state.setString(3, usernameA);
            state.setString(4, usernameB);
            state.setString(5, usernameA);
            state.setString(6, usernameB);
            state.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
