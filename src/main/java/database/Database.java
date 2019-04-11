package database;

import org.joda.time.Instant;
import server.Action;
import server.ActionList;
import server.Challenge;
import server.CompareFriends;
import server.FriendsList;
import server.TokenResponse;
import server.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Database is a class that will be used in communication with the server.
 */
public class Database {

    private static Connection connection;

    private static PreparedStatement getUsername;
    private static PreparedStatement getUser;
    private static PreparedStatement addActionOne;
    private static PreparedStatement addActionTwo;
    private static PreparedStatement retract;
    private static PreparedStatement getCarbonValues;
    private static PreparedStatement updateTotalScores;
    private static PreparedStatement getCarbonReduced;
    private static PreparedStatement getCarbonProduced;
    private static PreparedStatement getTotalScore;
    private static PreparedStatement getTotalScoreByUser;
    private static PreparedStatement silentLoginCheck;
    private static PreparedStatement registerOne;
    private static PreparedStatement registerTwo;
    private static PreparedStatement checkUsername;
    private static PreparedStatement checkLogin;
    private static PreparedStatement addFriend;
    private static PreparedStatement showFriends;
    private static PreparedStatement showFollowers;
    private static PreparedStatement getLeaderboard;
    private static PreparedStatement getLastMeal;
    private static PreparedStatement addChallenge;
    private static PreparedStatement retrieveChallenges;
    private static PreparedStatement initializeChallenge;
    private static PreparedStatement evaluateChallengesA;
    private static PreparedStatement evaluateChallengesB;
    private static PreparedStatement updateChallenge;
    private static PreparedStatement getRecentCOSavings;

    /**
     * This method keeps trying connecting to the database until successful
     */
    public static void connect() {
        try {
            connection = DriverManager.getConnection();
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("Unable to connect to the database");
            e.printStackTrace();
        }
    }

    /**
     * This method keeps trying connecting to the database until successful
     */
    public static void prepare() {
        try {

            getUsername = connection.prepareStatement(
                    "SELECT username "
                            + "FROM user_data "
                            + "WHERE token = ?;");

            getUser = connection.prepareStatement(
                    "SELECT user_data.username, user_data.mail, total_score.total_score "
                            + "FROM user_data "
                            + "JOIN total_score ON user_data.username = total_score.username "
                            + "WHERE user_data.token = ?;");

            addActionOne = connection.prepareStatement(
                    "SELECT action_id, parent_category "
                            + "FROM actions "
                            + "WHERE actions.action_name = ?;");

            addActionTwo = connection.prepareStatement(
                    "INSERT INTO events (action_id, date_time, points, "
                            + "parent_category, username, carbon_reduced, "
                            + "carbon_produced, description)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

            retract = connection.prepareStatement(
                    "SELECT actions.action_name, events.points, "
                            + "events.carbon_reduced, events.carbon_produced, events.date_time "
                            + "FROM events JOIN actions ON events.action_id = actions.action_id "
                            + "WHERE events.username = ? "
                            + "ORDER BY date_time DESC "
                            + "LIMIT 10;");

            getCarbonValues = connection.prepareStatement(
                    "SELECT carbon_produced, carbon_reduced "
                            + "FROM total_score JOIN user_data ON total_score.username = user_data.username "
                            + "WHERE user_data.token = ?;");

            updateTotalScores = connection.prepareStatement(
                    "UPDATE total_score "
                            + "SET total_score = ?, carbon_reduced = ?, carbon_produced = ? "
                            + "WHERE username = ?;");

            getCarbonReduced = connection.prepareStatement(
                    "SELECT carbon_reduced "
                            + "FROM total_score JOIN user_data ON total_score.username = user_data.username "
                            + "WHERE user_data.token = ?;");

            getCarbonProduced = connection.prepareStatement(
                    "SELECT carbon_produced "
                            + "FROM total_score "
                            + "JOIN user_data ON total_score.username = user_data.username "
                            + "WHERE user_data.token = ?;");

            getTotalScore = connection.prepareStatement(
                    "SELECT total_score "
                            + "FROM total_score "
                            + "JOIN user_data ON total_score.username = user_data.username "
                            + "WHERE user_data.token = ?;");

            getTotalScoreByUser = connection.prepareStatement(
                    "SELECT total_score "
                            + "FROM total_score "
                            + "WHERE total_score.username = ?;");

            silentLoginCheck = connection.prepareStatement(
                    "SELECT user_data.token "
                            + "FROM user_data "
                            + "WHERE user_data.token = ?;");

            registerOne = connection.prepareStatement(
                    "INSERT INTO user_data (token, username, hashpassword, mail)"
                            + "VALUES (?, ?, ?, ?);");

            registerTwo = connection.prepareStatement(
                    "INSERT INTO total_score (total_score, username)"
                            + "VALUES (0, ?);");

            checkUsername = connection.prepareStatement(
                    "SELECT user_data.username "
                            + "FROM user_data "
                            + "WHERE user_data.username = ?;");

            checkLogin = connection.prepareStatement(
                    "SELECT user_data.token "
                            + "FROM user_data "
                            + "WHERE user_data.username = ? AND user_data.hashpassword = ?;");

            addFriend = connection.prepareStatement(
                    "INSERT INTO friends (user_a, user_b) "
                            + "VALUES (?, ?);");

            showFriends = connection.prepareStatement(
                    "SELECT user_b "
                            + "FROM friends "
                            + "WHERE user_a = ?;");

            showFollowers = connection.prepareStatement(
                    "SELECT friends.user_a, total_score.total_score "
                            + "FROM friends "
                            + "JOIN total_score ON friends.user_a=total_score.username "
                            + "WHERE user_b = ?;");

            getLeaderboard = connection.prepareStatement(
                    "SELECT username, total_score "
                            + "FROM total_score "
                            + "ORDER BY total_score DESC "
                            + "LIMIT 10;");

            getLastMeal = connection.prepareStatement(
                    "SELECT date_time "
                            + "FROM events "
                            + "JOIN user_data ON events.username = user_data.username "
                            + "WHERE user_data.token = ? AND events.parent_category = 1 "
                            + "ORDER BY date_time DESC "
                            + "LIMIT 1;");

            addChallenge = connection.prepareStatement(
                    "INSERT INTO challenges (goal, user_a, user_b, state, time_added)"
                            + "VALUES (?, (SELECT username FROM user_data WHERE token = ?), ?, '0', ?);");

            retrieveChallenges = connection.prepareStatement(
                    "SELECT user_a, user_b, score_a, score_b, goal, state "
                            + "FROM challenges "
                            + "WHERE user_a = ? OR user_b = ?;");

            initializeChallenge = connection.prepareStatement(
                    "UPDATE challenges "
                            + "SET score_a = "
                            + "(SELECT total_score FROM total_score WHERE username = ?), "
                            + "score_b = "
                            + "(SELECT total_score FROM total_score WHERE username = ?), "
                            + "time_added = ? ,"
                            + "state = '1' "
                            + "WHERE user_a = ? AND user_b = ? AND state = '0'");

            evaluateChallengesA = connection.prepareStatement(
                    "SELECT score_a, goal, time_added, user_a, user_b "
                            + "FROM challenges "
                            + "WHERE user_a = ? AND state = '1';");

            evaluateChallengesB = connection.prepareStatement(
                    "SELECT score_b, goal, time_added, user_a, user_b "
                            + "FROM challenges "
                            + "WHERE user_b = ? AND state = '1';");

            updateChallenge = connection.prepareStatement(
                    "UPDATE challenges "
                            + "SET state = ? "
                            + "WHERE time_added = ? AND user_a = ? AND user_b = ?;");

            getRecentCOSavings = connection.prepareStatement(
                    "SELECT events.carbon_reduced "
                            + "FROM events "
                            + "JOIN user_data ON events.username = user_data.username "
                            + "WHERE user_data.token = ?"
                            + "ORDER BY events.date_time DESC "
                            + "LIMIT 30;");

            System.out.println("Statements prepared on the database");
        } catch (SQLException e) {
            System.out.println("Unable to prepare statements to the database");
            e.printStackTrace();
        }
    }

    /**
     * This method keeps trying disconnecting from the database until successful
     */
    public static void disconnect() {
        try {
            connection.close();
            System.out.println("Disconnected from the database");
        } catch (SQLException e) {
            System.out.println("Unable to disconnect from the database");
            e.printStackTrace();
        }
    }

    /**
     * This method gets the username from the database.
     * @param token A String with the token.
     * @return the username.
     */
    public static String getUsername(String token) {
        try {
            PreparedStatement state = getUsername;

            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            String userName = "";
            while (rs.next()) {
                userName = rs.getString(1);
                System.out.println("username: " + userName);

            }
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
            PreparedStatement state = getUser;

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
                return user;
            }
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
            System.out.println("addAction called");
            PreparedStatement state = addActionOne;

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


            PreparedStatement state1 = addActionTwo;

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

            evaluateChallenges(action.getToken());

            System.out.println("INSERT success");
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
            System.out.println("retract called");
            PreparedStatement state = retract;

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
            PreparedStatement state = getCarbonValues;

            state.setString(1, token);
            ResultSet rs = state.executeQuery();

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

            PreparedStatement state1 = updateTotalScores;

            state1.setInt(1, currentTotalScore);
            state1.setString(4, getUsername(token));
            state1.setDouble(2, currentCarbonReduced);
            state1.setDouble(3, currentCarbonProduced);
            state1.executeUpdate();
            System.out.println("UPDATE success");
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
            System.out.println("getCarbonReduced called");

            PreparedStatement state = getCarbonReduced;

            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            double currentCarbonReduced = 0;
            while (rs.next()) {
                currentCarbonReduced = rs.getDouble(1);
                System.out.println("carbon_reduced: " + currentCarbonReduced);
            }

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
            System.out.println("getCarbonProduced called");

            PreparedStatement state = getCarbonProduced;

            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            double currentCarbonProduced = 0;
            while (rs.next()) {
                currentCarbonProduced = rs.getDouble(1);
                System.out.println("carbon_reduced: " + currentCarbonProduced);
            }

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
        System.out.println("getTotalScore called");
        try {

            PreparedStatement state = getTotalScore;

            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            int currentTotalScore = 0;
            while (rs.next()) {
                currentTotalScore = rs.getInt(1);
                System.out.println("currentTotalScore: " + currentTotalScore);
            }

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
        System.out.println("getTotalScore called");
        try {
            PreparedStatement state = getTotalScoreByUser;

            state.setString(1, username);
            ResultSet rs = state.executeQuery();

            int currentTotalScore = 0;
            while (rs.next()) {
                currentTotalScore = rs.getInt(1);
                System.out.println("currentTotalScoreOfUser: " + currentTotalScore);
            }

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
        System.out.println("silentLogicCheck called");
        try {
            PreparedStatement state = silentLoginCheck;

            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                System.out.println("token found");
                return true;
            }
            System.out.println("token not found");
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
        System.out.println("register called");
        String defaultMail = "name@email.com";
        try {
            PreparedStatement state = registerOne;

            state.setString(1, token);
            state.setString(2, user.getName());
            state.setString(3, user.getPassword());
            state.setString(4, defaultMail);
            state.executeUpdate();

            PreparedStatement stateTwo = registerTwo;

            stateTwo.setString(1, getUsername(token));
            stateTwo.executeUpdate();

            System.out.println("INSERT success");
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
        System.out.println("checkUsername called (looking for '" + username + "')");
        try {
            PreparedStatement state = checkUsername;

            state.setString(1, username);

            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                System.out.println("--found: " + rs.getString(1));
                return true;
            }
            System.out.println("--not found");
            return false;
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
        try {
            PreparedStatement state = checkLogin;

            state.setString(1, user.getName());
            state.setString(2, user.getPassword());
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                return new TokenResponse(rs.getString(1), true);
            }

            return new TokenResponse(null, false);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new TokenResponse(null, false);
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
            PreparedStatement state = addFriend;

            state.setString(1, getUsername(friend.getToken()));
            state.setString(2, friend.getUsername());
            state.executeUpdate();
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
            PreparedStatement state = showFriends;

            state.setString(1, getUsername(token));
            ResultSet rs = state.executeQuery();

            ArrayList<CompareFriends> result = new ArrayList<>();
            while (rs.next()) {
                String         usernameFriend = rs.getString(1);
                int            score          = getTotalScoreByUser(usernameFriend);
                CompareFriends friend         = new CompareFriends(usernameFriend, score);
                result.add(friend);
            }
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
            PreparedStatement state = showFollowers;

            state.setString(1, getUsername(token));
            ResultSet rs = state.executeQuery();

            ArrayList<CompareFriends> result = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString(1);
                int    score    = rs.getInt(2);
                result.add(new CompareFriends(username, score));
            }

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
            PreparedStatement state = getLeaderboard;

            ResultSet rs = state.executeQuery();

            ArrayList<CompareFriends> result = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString(1);
                int    score    = rs.getInt(2);
                result.add(new CompareFriends(username, score));
            }

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
            PreparedStatement state = getLastMeal;

            state.setString(1, token);
            ResultSet rs = state.executeQuery();

            long time = 0;
            while (rs.next()) {
                time = rs.getLong(1);
            }

            return time;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     * This method add a challenge.
     * @param token token of user A
     * @param userB username of person being challenged
     * @param goal  score as the goal.
     * @return if the query succeeded.
     */
    public static boolean addChallenge(String token, String userB, int goal) {
        System.out.println("addChallenge called");
        try {
            PreparedStatement state = addChallenge;

            state.setInt(1, goal);
            state.setString(2, token);
            state.setString(3, userB);
            state.setLong(4, Instant.now().getMillis());
            state.executeUpdate();

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
    public static ArrayList<Challenge> retrieveChallenges(String token) {
        String user = getUsername(token);
        System.out.println("retrieveChallenge called for user: "+user);
        try {
            ArrayList<Challenge> list = new ArrayList<>();

            PreparedStatement state = retrieveChallenges;
            state.setString(1, user);
            state.setString(2, user);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                Challenge challenge = new Challenge();
                challenge.setUserA(rs.getString(1));
                challenge.setUserB(rs.getString(2));
                challenge.setScoreA(rs.getInt(3));
                challenge.setScoreB(rs.getInt(4));
                challenge.setGoal(rs.getInt(5));
                challenge.setState(rs.getInt(6));
                challenge.setOnA(challenge.getUserA().equals(user));

                list.add(challenge);
            }

            return list;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * This method updates a challenge when the other user accepts.
     * @param token    authorization token of the user initialising challenge
     * @param username username of the opponent
     * @return if the query succeeded.
     */
    public static boolean initializeChallenge(String token, String username) {
        System.out.println("initializeChallenge called");
        try {
            PreparedStatement state = initializeChallenge;

            String userA = username;
            String userB = getUsername(token);

            state.setString(1, userA);
            state.setString(2, userB);
            state.setLong(3, Instant.now().getMillis());
            state.setString(4, userA);
            state.setString(5, userB);

            return state.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void evaluateChallenges(String token) {
        String username = getUsername(token);
        int    score    = getTotalScore(token);
        System.out.println("--evaluating challanges for user: " + username);
        try {

            evaluateChallenge(username, score, evaluateChallengesA, 10);
            evaluateChallenge(username, score, evaluateChallengesB, 11);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void evaluateChallenge(String username, int score, PreparedStatement state, int update) throws SQLException {
        state.setString(1, username);

        ResultSet result = state.executeQuery();

        while (result.next()) {
            int start = result.getInt(1);
            int goal  = result.getInt(2);
            if (score >= start + goal) {

                PreparedStatement updateState = updateChallenge;

                updateState.setInt(1, update);
                updateState.setLong(2, result.getLong(3));
                updateState.setString(3, result.getString(4));
                updateState.setString(4, result.getString(5));

                updateState.executeUpdate();
            }
        }
    }

    /**
     * This method updates a atate of challenge.
     * @param time   the id of the challenge
     * @param userA  the id of the challenge
     * @param userB  the id of the challenge
     * @param update the updated state of challenge (0,1,10,11)
     * @return if the query succeeded.
     */
    public static boolean updateChallenge(Long time, String userA, String userB, int update) {
        System.out.println("updateChallenge called");
        try {
            PreparedStatement state = updateChallenge;

            state.setInt(1, update);
            state.setLong(2, time);
            state.setString(3, userA);
            state.setString(4, userB);

            return state.executeUpdate() == 1;
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
            PreparedStatement state = getRecentCOSavings;

            state.setString(1, token);

            ResultSet rs = state.executeQuery();

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
