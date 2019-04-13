package gogreen.server;

import gogreen.server.holders.Action;
import gogreen.server.holders.ActionList;
import gogreen.server.holders.Challenge;
import gogreen.server.holders.ChallengesList;
import gogreen.server.holders.CompareFriends;
import gogreen.server.holders.FriendsList;
import gogreen.server.holders.OnLoadValues;
import gogreen.server.holders.TokenResponse;
import gogreen.server.holders.User;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the communication with the server.
 */
public class Communication {

    private static String hostURL = "http://localhost:8080";
    private static String fileDir = "src/main/resources/extraFiles/Token.txt";
    private static String token   = null;

    /**
     * Setter for the hostURL.
     * @param url the hostURL
     */
    public static void setHostUrl(String url) {
        hostURL = url;
    }

    // ========== PART METHODS =================================================

    /**
     * Handles login and register with the server.
     * avoids duplicate code.
     * @param username the username
     * @param password the password
     * @param remember whether to store token in a file
     * @param postUrl  the postURL, determine between login and register
     * @return boolean whether the submit/fetch was successful
     */
    private static boolean submit(
            String username, String password, boolean remember, String postUrl) {
        User             user    = new User(username, password);
        HttpEntity<User> message = new HttpEntity<>(user);

        RestTemplate request = new RestTemplate();
        TokenResponse response =
                request.postForObject(hostURL + postUrl, message, TokenResponse.class);

        if (!response.isLegit()) {
            System.out.println("not found");
            // not matching login and password
            return false;
        }

        token = response.getToken();
        if (remember) {
            try {
                FileWriter out = new FileWriter(new File(fileDir));

                //save token to file
                out.write(token);
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println("Wrong directory for token file");
            }
        }

        return true;
    }

    /**
     * sends the user's token to the server and retrieves a given request from it, then returns it.
     * @param url    url to query to in the server
     * @param expect what class to expect the server to return
     * @return the answer from the server of type "expect"
     */
    private static Object postToken(String url, Class expect) {
        HttpEntity<String> message = new HttpEntity<>(token);
        RestTemplate       request = new RestTemplate();
        return request.postForObject(hostURL + url, message, expect);
    }


    // ========== USER AUTHENTICATION ==========================================

    /**
     * Checks whether a given username is not already taken on the server.
     * Stores the username and password, retrieves assigned token.
     * @param username the username
     * @param password the password
     * @param remember whether to store the token in a file
     * @return boolean correctly logged in and if the token was received
     */
    public static boolean register(String username, String password, boolean remember) {
        //send username and password to the server, validate if it is not taken
        //expect generated token if successful, null if username taken
        ComCached.setUserChanged(null);
        return submit(username, password, remember, "/register");
    }

    /**
     * Checks whether a given username and password matches on the server.
     * If yes, retrieves token for such combination for further communication
     * @param username the username
     * @param password the password
     * @param remember whether to store the token in a file
     * @return boolean correctly logged in and token received
     */
    public static boolean login(String username, String password, boolean remember) {
        //validate if username and password matched those on server
        //if they do retrieve token and store it
        ComCached.setUserChanged(null);
        return submit(username, password, remember, "/login");
    }

    /**
     * Tries to log in with the eventually stored username and password.
     * @return boolean correctly logged in and token received
     */
    public static boolean silentLogin() {
        //retrieve username and password from somewhere (file)
        //try to log in and retrieve token

        token = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(fileDir));
            token = in.readLine();
            in.close();
        } catch (IOException e) {
            System.out.println("No token file found");
            return false;
        }

        return (boolean) postToken("/silentLogin", boolean.class);
    }

    /**
     * Removes cached information about the user and logs out.
     */
    public static void logout() {
        ComCached.setUserChanged(null);
        ComCached.setTotalChanged(null);
        ComCached.setLastChanged(null);
        ComCached.setCoChanged(null);
        ComCached.setCarbonChanged(null);
        ComCached.setFriendsChanged(null);

        // remove token from Main Memory
        token = null;

        // remove token from Secondary Memory
        File file = new File(fileDir);
        file.delete();
    }

    // ========== ACTION HANDLERS ==============================================

    /**
     * adding an action to the server.database.
     * @param actionName     the name of the action
     * @param points         the points for the action
     * @param carbonReduced  the carbon reduced in the action
     * @param carbonProduced the carbon produced in the action
     */
    public static boolean addAction(String actionName, int points,
                                    double carbonReduced, double carbonProduced) {
        return addAction(actionName, points, carbonReduced, carbonReduced, null);
    }

    /**
<<<<<<< HEAD:src/main/java/client/Communication.java
     * adding an action to the database, removes all the cached information
     * about CO2 reductions and the user's score.
=======
     * to be implemented:
     * adding an action to the server.database.
>>>>>>> e016a38735b28bfbefe56c49d701d2d53e89830f:src/main/java/gogreen/server/Communication.java
     * @param actionName     the name of the action
     * @param points         the points for the action
     * @param carbonReduced  the carbon reduced in the action
     * @param carbonProduced the carbon produced in the action
     */
    public static boolean addAction(String actionName, int points, double carbonReduced,
                                    double carbonProduced, String description) {
        ComCached.setTotalChanged(null);
        ComCached.setLastChanged(null);
        ComCached.setCoChanged(null);
        ComCached.setCarbonChanged(null);

        Action action = new Action(token, actionName,
                points, carbonReduced, carbonProduced, description);
        HttpEntity<Action> message = new HttpEntity<>(action);
        RestTemplate       request = new RestTemplate();

        return request.postForObject(hostURL + "/addAction", message, boolean.class);
    }

    /**
     * Sends a request to the server to retrieve the user's last three actions.
     * @return string containing the user's last three actions
     */
    public static ArrayList<Action> getLastThreeActions() {
        return ((ActionList) postToken("/retract", ActionList.class)).getList();
    }


    /**
     * Sends a request to the server to retrieve the user's total score.
     * @return the user's total score
     */
    public static int getMyTotalScore() {
        return (int) postToken("/getTotalScore", Integer.class);
    }

    // ========== SOCIAL HANDLERS ==============================================

    /**
     * This method checks if the searched username exists in the database or not.
     * @param username username
     * @return whether the username exists
     */
    public static boolean checkUsername(String username) {
        HttpEntity<String> message = new HttpEntity<>(username);
        RestTemplate       request = new RestTemplate();
        return request.postForObject(hostURL + "/searchUser", message, boolean.class);
    }

    /**
     * This method is to get the general information about the user.
     * @return the user's general information (User.class)
     */
    public static User getUser() {
        return (User) postToken("/getUser", User.class);
    }

    /**
     * This method adds a friend by the friend's username.
     * a Friend is someone the user follows.
     * @param friendUsername username of the friend
     * @return whether the user's friend is added successfully
     */
    public static boolean addFriend(String friendUsername) {
        ComCached.setFriendsChanged(null);

        CompareFriends friend = new CompareFriends(token, friendUsername);

        HttpEntity<CompareFriends> message = new HttpEntity<>(friend);
        RestTemplate               request = new RestTemplate();
        return request.postForObject(hostURL + "/addFriend", message, boolean.class);
    }

    /**
     * This method retrieves the user's list of friends from the server.
     * A friend is someone the user follows.
     * @return the user's list of friends
     */
    public static ArrayList<CompareFriends> getFriends() {
        return ((FriendsList) postToken("/showFriends", FriendsList.class)).getList();
    }

    /**
     * This method retrieves the user's list of followers from the server.
     * A follower is someone who follows the user.
     * @return the user's followers"
     */
    public static ArrayList<CompareFriends> getFollowers() {
        return ((FriendsList) postToken("/showFollowers", FriendsList.class)).getList();
    }

    /**
     * Getter for the leaderboard from the Server.
     * @return the leaderboard
     */
    public static ArrayList<CompareFriends> getLeaderboard() {
        RestTemplate request = new RestTemplate();
        return request.getForObject(hostURL + "/getLeaderboard", FriendsList.class).getList();
    }

    /**
     * Getter for the user's total CO2 reduction.
     * @return the user's total CO2 reduction
     */
    public static Action carbon() {
        return (Action) postToken("/carbon", Action.class);
    }

    /**
     * Getter for the user's OnLoadValues.
     * @return the user's OnLoadValues
     */
    public static OnLoadValues onLoad() {
        return (OnLoadValues) postToken("/onLoad", OnLoadValues.class);
    }

    /**
     * Adds a challenge.
     * @param username the user's username
     * @param goal     the goal of the challenge
     * @return whether the challenge was added successfully
     */
    public static boolean addChallenge(String username, int goal) {
        CompareFriends challenge = new CompareFriends();
        challenge.setToken(token);
        challenge.setUsername(username);
        challenge.setScore(goal);
        HttpEntity<CompareFriends> message = new HttpEntity<>(challenge);
        RestTemplate               request = new RestTemplate();
        return request.postForObject(hostURL + "/addChallenge", message, boolean.class);
    }

    /**
     * Getter for the user's list of challenges from the server.
     * @return the user's list of challenges
     */
    public static ArrayList<Challenge> getChallenges() {
        return ((ChallengesList) postToken("/getChallenges", ChallengesList.class)).getList();
    }

    /**
     * This method is to accept a challenge.
     * @param challenge the challenge to accept
     * @return whether the challenge was successfully accepted
     */
    public static boolean acceptChallenge(Challenge challenge) {
        challenge.setUserB(token);
        HttpEntity<Challenge> message = new HttpEntity<>(challenge);
        RestTemplate          request = new RestTemplate();
        return request.postForObject(hostURL + "/acceptChallenge", message, boolean.class);
    }

    /**
     * Getter for the user's recent CO2 reductions (cap 30 actions).
     * ordered from last (at 0) to the most recent (last)
     * @return the user's recent CO2 reductions
     */
    public static double[] getRecentCoSavings() {
        ArrayList<Action> actions = ((ActionList) postToken("/getRecentCOSavings",
                ActionList.class)).getList();

        double[] arr = new double[actions.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[arr.length - i - 1] = actions.get(i).getCarbonReduced();
        }

        return arr;
    }
}
