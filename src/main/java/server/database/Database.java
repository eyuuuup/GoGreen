package server.database;

import org.joda.time.Instant;
import server.holders.Action;
import server.holders.ActionList;
import server.holders.Challenge;
import server.holders.CompareFriends;
import server.holders.FriendsList;
import server.holders.OnLoadValues;
import server.holders.TokenResponse;
import server.holders.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents the communication with the database.
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
    private static PreparedStatement getOTE;
    private static PreparedStatement addChallenge;
    private static PreparedStatement retrieveChallenges;
    private static PreparedStatement initializeChallenge;
    private static PreparedStatement evaluateChallengesA;
    private static PreparedStatement evaluateChallengesB;
    private static PreparedStatement updateChallenge;
    private static PreparedStatement getRecentCOSavings;

    /**
     * Connects to the database.
     */
    public static void connect() {
        try {
            connection = DriverManager.getConnection();
            System.out.println("Connected to the server.database");
        } catch (SQLException e) {
            System.out.println("Unable to connect to the server.database");
            e.printStackTrace();
        }
    }

    /**
     * Caches the prepareStatements for the database queries.
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
                            + "events.carbon_reduced, events.carbon_produced, "
                            + "events.date_time, events.description "
                            + "FROM events JOIN actions ON events.action_id = actions.action_id "
                            + "WHERE events.username = ? "
                            + "ORDER BY date_time DESC "
                            + "LIMIT 10;");

            getCarbonValues = connection.prepareStatement(
                    "SELECT carbon_produced, carbon_reduced "
                            + "FROM total_score JOIN user_data ON "
                            + "total_score.username = user_data.username "
                            + "WHERE user_data.token = ?;");

            updateTotalScores = connection.prepareStatement(
                    "UPDATE total_score "
                            + "SET total_score = ?, carbon_reduced = ?, carbon_produced = ? "
                            + "WHERE username = ?;");

            getCarbonReduced = connection.prepareStatement(
                    "SELECT carbon_reduced "
                            + "FROM total_score JOIN user_data "
                            + "ON total_score.username = user_data.username "
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

            getOTE = connection.prepareStatement(
                    "SELECT action_id "
                            + "FROM events "
                            + "JOIN user_data ON events.username = user_data.username "
                            + "WHERE user_data.token = ? AND "
                            + "(action_id = 14 OR action_id = 13 OR action_id = 12)"
                            + "ORDER BY action_id DESC;");

            addChallenge = connection.prepareStatement(
                    "INSERT INTO challenges (goal, user_a, user_b, state, time_added)"
                            + "VALUES (?, (SELECT username FROM user_data "
                            + "WHERE token = ?), ?, '0', ?);");

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

            System.out.println("Statements prepared on the server.database");
        } catch (SQLException e) {
            System.out.println("Unable to prepare statements to the server.database");
            e.printStackTrace();
        }
    }

    /**
     * Disconnects from the database.
     */
    public static void disconnect() {
        try {
            connection.close();
            System.out.println("Disconnected from the server.database");
        } catch (SQLException e) {
            System.out.println("Unable to disconnect from the server.database");
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the username from the database.
     * @param token the user's token
     * @return the user's username
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
     * Retrieves the user's general information from the database.
     * @param token the user's token
     * @return the user's general information.
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
     * Adds action to the database.
     * @param action the action to add
     * @return if the action was added successfully
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
     * Retrieves the user's last three actions from the database.
     * @param token the user's token
     * @return the user's last three actions
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
                action.setDescription(rs.getString(6));
                
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
     * Retrieves the user's total CO2 reduction from the database.
     * @param token the user's token
     * @return the user's total CO2 reduction
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
     * Updates the user's total score in the database.
     * @param token the user's token
     * @param score the score to be added to the total score
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
     * Retrieves the user's recent CO2 reductions from the database.
     * @param token the user's token
     * @return the user's recent CO2 reductions
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
     * Retrieve the user's total CO2 production.
     * @param token the user's token
     * @return the user's total CO2 production
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
     * Retrieves the user's total score.
     * @param token the user's token
     * @return the user's total score
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
     * Retrieves the user's total score.
     * @param username the user's username
     * @return the user's total score
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
     * Tries silent login with a stored token.
     * @param token the user's stored token
     * @return whether logged in
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
     * Registers a new user in the database.
     * @param user  the new user.
     * @param token the new user's token.
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
     * Checks if the given username is already in the database.
     * @param username the username to check for
     * @return whether the username is already in the database
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
     * Tries to log in.
     * @param user the user.
     * @return a TokenResponse with a token for further communication
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
     * Adds User B as a friend of User A in the database.
     * @param friend the friend.
     * @return whether the friend was added successfully.
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
     * Retrieves the user's list of friends from the database.
     * @param token the user's token
     * @return the user's list of friends
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
     * Retrieves the user's list of followers from the database.
     * @param token the user's token
     * @return the user's list of followers.
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
     * Retrieves the leaderboard from the database.
     * @return the leaderboard
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
     * Retrieves the user's last meal from the database.
     * @param token the user's last meal
     * @return the time of the last meal
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
     * Retrieves the user's one time events from the database.
     * @param token the user's token
     * @return the user's one time events
     */
    public static OnLoadValues getOte(String token) {
        System.out.println("getOte called");
        try {
            PreparedStatement state = getOTE;
            state.setString(1, token);

            OnLoadValues olv = new OnLoadValues();
            olv.setSolarPanel(false);
            olv.setElectricCar(false);
            olv.setEnvGroup(false);

            ResultSet    rs  = state.executeQuery();
            while (rs.next()) {
                int number = rs.getInt(1);
                if (number == 12) {
                    olv.setSolarPanel(true);
                }
                if (number == 13) {
                    olv.setElectricCar(true);
                }
                if (number == 14) {
                    olv.setEnvGroup(true);
                }
            }
            return olv;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new OnLoadValues();
        }
    }

    /**
     * Adds a challenge to the database.
     * @param token the user's token
     * @param userB username of the user being challenged
     * @param goal  the goal of the challenge in points
     * @return whether the challenge was added successfully
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
     * Retrieves the user's list of challenges from the database.
     * @param token the user's token
     * @return the user's challenges
     */
    public static ArrayList<Challenge> retrieveChallenges(String token) {
        String user = getUsername(token);
        System.out.println("retrieveChallenge called for user: " + user);
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
     * Updates the accepted challenge in the database.
     * @param token    the user's token who is initializing the challenge
     * @param username username of the opponent
     * @return whether the challenge was initialized successfully
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

    /**
     * Evaluates the user's challenges in the database.
     * @param token the user's token
     */
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

    /**
     * Evaluates a challenge of the user in the database.
     * @param username the user's username
     * @param score    the user's score
     * @param state    the state of the challenge
     * @param update   update the updated state of challenge (0,1,10,11)
     * @throws SQLException if the query results in an exception
     */
    private static void evaluateChallenge(String username, int score, PreparedStatement state,
                                          int update) throws SQLException {
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
     * This method updates the state of challenge in the database.
     * @param time   the start time of the challenge
     * @param userA  user A of the challenge
     * @param userB  user B of the challenge
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
     * Retrieves the user's recent CO2 reductions (30 actions cap).
     * ordered from the most recent (at 0) to last (last).
     * @return the user's recent CO2 reductions
     */
    public static ArrayList<Action> getRecentCoSavings(String token) {
        System.out.println("get recent CO2 savings called");
        try {
            PreparedStatement state = getRecentCOSavings;

            state.setString(1, token);

            ResultSet rs = state.executeQuery();

            ArrayList<Action> list = new ArrayList<>();
            while (rs.next()) {
                Action action = new Action();
                action.setCarbonReduced(rs.getDouble(1));
                list.add(action);
            }
            System.out.println();
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
