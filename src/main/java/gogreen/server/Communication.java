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

public class Communication {

    private static String hostURL = "http://localhost:8080";
    private static String fileDir = "src/main/resources/extraFiles/Token.txt";
    private static String token   = null;

    /**
     * Setter for hostURL
     * @param url of the new path
     */
    public static void setHostURL(String url) {
        hostURL = url;
    }

    // ========== PART METHODS =================================================

    /**
     * Handles login and register.
     * avoid duplicate code.
     * @param username the username
     * @param password the password
     * @param remember wether to store token in a file
     * @param postUrl  determine between login and register
     * @return boolean wether the submit/fetch was sucessful
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
     * sends the user's token to the server and retrieves whatever from it, then returns it.
     * @param url    url at which to querry  the server
     * @param expect what class to return and expect from server
     * @return the answer from the server of the class "expect"
     */
    private static Object postToken(String url, Class expect) {
        HttpEntity<String> message = new HttpEntity<>(token);
        RestTemplate       request = new RestTemplate();
        return request.postForObject(hostURL + url, message, expect);
    }


    // ========== USER AUTHENTICATION ==========================================

    /**
     * Checks whether a given username is not taken on the server.
     * Stores the username and password, retrieves assigned token.
     * @param username the username
     * @param password the password
     * @param remember whether to store token in a file
     * @return boolean correctly logged in and token recieved
     */
    public static boolean register(String username, String password, boolean remember) {
        //send username and password to the server, validate if it is not taken
        //expect generated token if successful, null if username taken
        ComCached.setUserChanged(null);
        return submit(username, password, remember, "/register");
    }

    /**
     * Checks whether a given username and password matches on the server.
     * If yes, retrieves token for such combination for further authentication
     * @param username the username
     * @param password the password
     * @param remember wether to store token in a file
     * @return boolean correctly logged in and token recieved
     */
    public static boolean login(String username, String password, boolean remember) {
        //validate if username and password matched those on server
        //if they do retrieve token and store it
        ComCached.setUserChanged(null);
        return submit(username, password, remember, "/login");
    }

    /**
     * Tries to log in with the stored username and password.
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
     * Removes traces of previous user.
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
     * to be implemented:
     * adding an action to the server.database.
     * @param actionName     the name of the action
     * @param points         the points for the action
     * @param carbonReduced  the carbon reduced in the action
     * @param carbonProduced the carbon produced in the action
     */
    public static boolean addAction(String actionName, int points,
                                    double carbonReduced, double carbonProduced, String description) {
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
     * Sends request to the server to retrieve last three actions for current user.
     * @return string containing last three actions
     */
    public static ArrayList<Action> getLastThreeActions() {
        return ((ActionList) postToken("/retract", ActionList.class)).getList();
    }


    /**
     * Sends request to the server to retrieve this user's total score.
     * @return integer containing total score
     */
    public static int getMyTotalScore() {
        return (int) postToken("/getTotalScore", Integer.class);
    }

    // ========== SOCIAL HANDLERS ==============================================

    /**
     * This method checks if the searched username exists or not.
     * @param username username
     * @return if username exists
     */
    public static boolean checkUsername(String username) {
        HttpEntity<String> message = new HttpEntity<>(username);
        RestTemplate       request = new RestTemplate();
        return request.postForObject(hostURL + "/searchUser", message, boolean.class);
    }

    /**
     * This method is to get the information of user for the user.
     * @return username object containg all information about the user
     */
    public static User getUser() {
        return (User) postToken("/getUser", User.class);
    }

    /**
     * This method adds a friend by it's username.
     * Friend is someone who you follow.
     * @param friendUsername username of the friend
     * @return whether the friend is added
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
     * Friend is someone who you follow.
     * @return an arraylist ofCompareFriends
     */
    public static ArrayList<CompareFriends> getFriends() {
        return ((FriendsList) postToken("/showFriends", FriendsList.class)).getList();
    }

    /**
     * This method retrieves the user's list of followers from the server.
     * Follower is someone who follows you.
     * @return an arraylist of "CompareFriends"
     */
    public static ArrayList<CompareFriends> getFollowers() {
        return ((FriendsList) postToken("/showFollowers", FriendsList.class)).getList();
    }

    /**
     * This method is to get the list of top ten users for the leaderboard.
     * @return an object containing a list of users
     */
    public static ArrayList<CompareFriends> getLeaderboard() {
        RestTemplate request = new RestTemplate();
        return request.getForObject(hostURL + "/getLeaderboard", FriendsList.class).getList();
    }

    /**
     * This method is to get get carbon produced and reduced by the user.
     * @return carbon Action object with carbonReduced and carbonProduced values
     */
    public static Action carbon() {
        return (Action) postToken("/carbon", Action.class);
    }

    /**
     * This method is to get the values needed on the loading of the application.
     * @return an instance of onLoadValue class
     */
    public static OnLoadValues onLoad() {
        return (OnLoadValues) postToken("/onLoad", OnLoadValues.class);
    }

    public static boolean addChallenge(String username, int goal) {
        CompareFriends challenge = new CompareFriends();
        challenge.setToken(token);
        challenge.setUsername(username);
        challenge.setScore(goal);
        HttpEntity<CompareFriends> message = new HttpEntity<>(challenge);
        RestTemplate               request = new RestTemplate();
        return request.postForObject(hostURL + "/addChallenge", message, boolean.class);
    }

    public static ArrayList<Challenge> getChallenges() {
        return ((ChallengesList) postToken("/getChallenges", ChallengesList.class)).getList();
    }

    public static boolean acceptChallenge(Challenge challenge) {
        challenge.setUserB(token);
        HttpEntity<Challenge> message = new HttpEntity<>(challenge);
        RestTemplate          request = new RestTemplate();
        return request.postForObject(hostURL + "/acceptChallenge", message, boolean.class);
    }

    /**
     * This method returns the CO2 reduced of current user of last (cap 30) actions
     * ordered from last (at 0) to the most recent (last)
     * @return the filtered list containing only CO2 saved
     */
    public static double[] getRecentCOSavings() {
        ArrayList<Action> actions = ((ActionList) postToken("/getRecentCOSavings", ActionList.class)).getList();

        double[] arr = new double[actions.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[arr.length - i - 1] = actions.get(i).getCarbonReduced();
        }

        return arr;
    }
}
