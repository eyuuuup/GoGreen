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
     * mail and totalscore of user, found by token.
     * @param token String, token of the user
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
            
            int actionId       = 0;
            int parentCategory = 0;
            
            while (rs.next()) {
                actionId = rs.getInt(1);
                parentCategory = rs.getInt(2);
            }
            
            if (parentCategory == 1) {
                long last = getLastMeal(action.getToken());
                long now  = Instant.now().getMillis();
                
                long max = 12 * 60 * 60 * 1000;
                long min = 1000;
                System.out.println("now:" + now + "\tlast:" + last);
                if ((min < now - last) && (now - last < max)) {
                    return false;
                }
            }
            
            
            PreparedStatement state1 =
                    con.prepareStatement("INSERT INTO events (action_id, date_time, "
                            + "points, parent_category, username, carbon_reduced, carbon_produced, description)"
                            + "VALUES (?, ?, ?, ?, ?,?,?, ?);");
            state1.setInt(1, actionId);
            
            Long outputDate = Instant.now().getMillis();
            
            state1.setLong(2, outputDate);
            state1.setInt(3, action.getValue());
            state1.setInt(4, parentCategory);
            state1.setString(5, getUsername(action.getToken()));
            state1.setDouble(6, action.getCarbonReduced());
            state1.setDouble(7, action.getCarbonProduced());
            state1.setString(8, action.getDescription());
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
                            + "ORDER BY date_time DESC LIMIT 10");
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
    
    /**
     * This method is for getting carbon reduced and produced.
     * @param token String token of the user
     * @return ACtion object wth carbon values
     */
    
    public static Action getCarbonValues(String token) {
        System.out.println("get carbon values called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state =
                    con.prepareStatement("SELECT carbon_produced, carbon_reduced "
                            + "FROM total_score JOIN user_data ON total_score.username "
                            + "= user_data.username " + "WHERE user_data.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();
            
            con.close();
            
            if (rs.next()) {
                Action action = new Action();
                action.setCarbonProduced(rs.getDouble(1));
                action.setCarbonReduced(rs.getDouble(2));
                System.out.println("carbon_produced: " + action.getCarbonProduced()
                        + "\tcarbon_reduced: " + action.getCarbonReduced());
                
                return action;
            }
            return null;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     * This method updates the total score of a user.
     * @param token the token from a user.
     * @param score the score that should be added to the total.
     */
    public static void updateTotalScores(String token, int score,
                                         double carbonReduced, double carbonProduced) {
        try {
            System.out.println("updateTotalScores called");
            
            int    currentTotalScore     = getTotalScore(token);
            Action action                = Database.getCarbonValues(token);
            double currentCarbonReduced  = action.getCarbonReduced();
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
     * returns the carbon reduction.
     * @param token token
     * @return carbon reduction
     */
    public static double getCarbonReduced(String token) {
        try {
            Connection con = DriverManager.getConnection();
            System.out.println("getCarbonReduced called");
            
            PreparedStatement state =
                    con.prepareStatement("SELECT carbon_reduced "
                            + "FROM total_score JOIN user_data ON "
                            + "total_score.username = user_data.username "
                            + "WHERE user_data.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();
            
            double currentCarbonReduced = 0;
            while (rs.next()) {
                currentCarbonReduced = rs.getDouble(1);
                System.out.println("carbon_reduced: " + currentCarbonReduced);
            }
            
            con.close();
            return currentCarbonReduced;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    
    /**
     * get carbon produced.
     * @param token token
     * @return carbon produced
     */
    public static double getCarbonProduced(String token) {
        try {
            Connection con = DriverManager.getConnection();
            System.out.println("getCarbonProduced called");
            
            PreparedStatement state =
                    con.prepareStatement("SELECT carbon_produced "
                            + "FROM total_score JOIN user_data ON "
                            + "total_score.username = user_data.username "
                            + "WHERE user_data.token = ?");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();
            
            double currentCarbonProduced = 0;
            while (rs.next()) {
                currentCarbonProduced = rs.getDouble(1);
                System.out.println("carbon_reduced: " + currentCarbonProduced);
            }
            
            con.close();
            return currentCarbonProduced;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    
    /**
     * This method queries the database to get the total score of a user.
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
                System.out.println("currentTotalScoreOfUser: " + currentTotalScore);
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
                String         usernameFriend = rs.getString(1);
                int            score          = getTotalScoreByUser(usernameFriend);
                CompareFriends friend         = new CompareFriends(usernameFriend, score);
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
                int    score    = rs.getInt(2);
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
                int    score    = rs.getInt(2);
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
                            + "WHERE user_data.token = ? AND events.parent_category=1 "
                            + "ORDER BY date_time DESC LIMIT 1");
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
     * This methods gets the OTE.
     * @param token A String of the token of the user.
     * @return ArrayList with the booleans of which events exist
     */
    public static boolean[] getOTE(String token) {
        System.out.println("getOTE called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "SELECT action_id "
                            + "FROM events JOIN user_data ON "
                            + "events.username = user_data.username "
                            + "WHERE user_data.token = ? AND (action_id = 14 OR action_id = 13 OR action_id = 12)"
                            + "ORDER BY action_id DESC");
            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            boolean[] result = {false, false, false};
            while (rs.next()) {
                switch(rs.getInt(1)){
                    case 12: result[0] = true;
                    case 13: result[1] = true;
                    case 14: result[2] = true;
                }
            }
            con.close();

            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new boolean[]{};
        }
    }


    /**
     * This method add a challenge.
     * @param challenge CompareFriend object with token of user A,
     *                  username of person being challenged, and score as the goal.
     * @return if the query succeeded.
     */
    public static boolean addChallenge(CompareFriends challenge) {
        System.out.println("addChallenge called");
        try {
            
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "INSERT INTO "
                            + "challenges (goal, user_a, user_b)"
                            + "VALUES (?, ?, ?)");
            state.setInt(1, challenge.getScore());
            state.setString(2, getUsername(challenge.getToken()));
            state.setString(3, challenge.getUsername());
            state.executeUpdate();
            
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * This method updates a challenge.
     * @param token the token of the user that wants to update.
     * @return if the query succeeded.
     */
    public static boolean updateChallenge(String token) {
        System.out.println("updateChallenge called");
        try {
            String     username = getUsername(token);
            Connection con      = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "UPDATE "
                            + "challenges SET score_a = "
                            + "(SELECT total_score FROM total_score WHERE username = ?), "
                            + "score_b = "
                            + "(SELECT total_score FROM total_score WHERE username = ?) "
                            + "WHERE user_a = ?");
            
            state.setString(1, username);
            state.setString(2, username);
            state.setString(3, username);
            state.executeUpdate();
            
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * This method is to give list of ongoing challenges and to be accepted challengesChallengesList
     * @param token
     * @return
     */
    public static ChallengesList retrieveChallenge(String token) {
        System.out.println("retrieveChallenge called");
        try {
            String     username = getUsername(token);
            Connection con      = DriverManager.getConnection();
            //accepted or ongoing challenge challenges
            PreparedStatement stateA = con.prepareStatement("SELECT user_a, user_b, score_a, score_b, goal FROM challenges "
                    + "WHERE user_a = ? AND score_a <> -1");
            stateA.setString(1, username);
            ResultSet            rs    = stateA.executeQuery();
            ArrayList<Challenge> listA = new ArrayList<>();
            
            while (rs.next()) {
                Challenge challenge = new Challenge();
                String    userA     = rs.getString(1);
                String    userB     = rs.getString(2);
                int       scoreA    = rs.getInt(3);
                int       scoreB    = rs.getInt(4);
                int       goal      = rs.getInt(5);
                
                challenge.setUserA(userA);
                challenge.setUserB(userB);
                challenge.setScoreA(scoreA);
                challenge.setScoreB(scoreB);
                challenge.setGoal(goal);
                listA.add(challenge);
            }
            
            //Challenges recieved by a user and not yet accepted
            PreparedStatement stateB = con.prepareStatement(
                    "SELECT user_a, user_b, score_a, score_b, goal "
                    + "FROM challenges "
                    + "WHERE user_b = ? AND score_b = -1");
            
            stateB.setString(1, username);
            ResultSet            rs2   = stateB.executeQuery();
            ArrayList<Challenge> listB = new ArrayList<>();
            
            while (rs.next()) {
                Challenge challenge = new Challenge();
                String    userA     = rs2.getString(1);
                String    userB     = rs2.getString(2);
                int       scoreA    = rs2.getInt(3);
                int       scoreB    = rs2.getInt(4);
                int       goal      = rs2.getInt(5);
                
                challenge.setUserA(userA);
                challenge.setUserB(userB);
                challenge.setScoreA(scoreA);
                challenge.setScoreB(scoreB);
                challenge.setGoal(goal);
                listB.add(challenge);
            }
            
            con.close();
            ChallengesList c = new ChallengesList(listA, listB);
            return c;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * This method updates a challenge when the other user accepts.
     * @param challenge The CompareFriends object.
     * @return if the query succeeded.
     */
    public static boolean initializeChallenge(CompareFriends challenge) {
        System.out.println("initializeChallenge called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "UPDATE challenges SET score_a = "
                            + "(SELECT total_score FROM total_score WHERE username = ?), "
                            + "score_b = "
                            + "(SELECT total_score FROM total_score WHERE username = ?)"
                            + ", time_added = ? WHERE user_a = ? AND user_b = ?");
            
            String userA = challenge.getUsername();
            String userB = getUsername(challenge.getToken());
            state.setString(1, userA);
            state.setString(2, userB);
            state.setLong(3, Instant.now().getMillis());
            state.setString(4, userA);
            state.setString(5, userB);
            state.executeUpdate();

            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * This method returns the CO2 reduced of current user of last (cap 30) actions
     * ordered from the most recent (at 0) to last (last)
     * @return the ActionList containing Action objects of CO2 saved
     */
    public static ArrayList<Action> getRecentCOSavings(String token) {
        System.out.println("get recent CO2 savings called");
        try {
            Connection con = DriverManager.getConnection();
            PreparedStatement state = con.prepareStatement(
                    "SELECT events.carbon_reduced " +
                            "FROM events " +
                            "JOIN user_data ON events.username = user_data.username " +
                            "WHERE user_data.token = ?" +
                            "ORDER BY events.date_time DESC " +
                            "LIMIT 30");
            state.setString(1, token);
            
            ResultSet rs = state.executeQuery();
            con.close();
            
            ArrayList<Action> list = new ArrayList<>();
            while (rs.next()) {
                Action a = new Action();
                a.setCarbonReduced(rs.getDouble(1));
                list.add(a);
            }
            System.out.println();
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
