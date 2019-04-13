package server;

import database.Database;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import javax.validation.Valid;

/**
 * This class represents the communication with the client.
 */

@RestController
@RequestMapping("/")
public class Controller {

    // ========== USER AUTHENTICATION ==========================================

    /**
     * This method checks whether the given user is in the database, to log in.
     * This is also the start of the client server connection.
     * @param user the user with the user's username and password
     * @return TokenResponse with the token for the connection, and whether the token is legit
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static TokenResponse login(@Valid @RequestBody User user) {
        //if(check in database)
        return Database.checkLogin(user);
    }

    /**
     * Register a new user, checks if the username is already taken or not
     * and generates a new token.
     * @param user the user with the user's username and password
     * @return TokenResponse with the token and whether the token is legit
     */
    @RequestMapping(value = {"/register"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static TokenResponse register(@Valid @RequestBody User user) {
        String token = null;
        boolean bool = Database.checkUsername(user.getName());
        if (!bool) {
            //generate TOKEN
            token = UUID.randomUUID().toString();
            Database.register(user, token);
            return new TokenResponse(token, true);
        } else {
            return new TokenResponse(null, false);
        }
    }

    /**
     * This method is used when the client wants to silent login using a stored token.
     * @param token the client's token
     * @return whether the client's token is legit
     */
    @RequestMapping(value = {"/silentLogin"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean silentLogin(@Valid @RequestBody String token) {
        return Database.silentLoginCheck(token);
    }

    // ========== ACTION HANDLERS ==============================================

    /**
     * Adds the user's action to the database.
     * @param action the user's action
     * @return whether the action is added successfully
     */
    @RequestMapping(value = {"/addAction"}, method = RequestMethod.POST)
    public static boolean addAction(@Valid @RequestBody Action action) {
        return Database.addAction(action);
    }

    /**
     * Getter for the user's last three actions from the database.
     * @param token the user's token
     * @return the user's last three actions
     */
    @RequestMapping(value = {"/retract"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static ActionList forDemo(@Valid @RequestBody String token) {
        return Database.retract(token);
    }

    /**
     * Getter for the user's total score from the database.
     * @param token the user's token
     * @return the user's total score
     */
    @RequestMapping(value = {"/getTotalScore"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static int totalScore(@Valid @RequestBody String token) {
        return Database.getTotalScore(token);
    }

    // ========== SOCIAL HANDLERS ==============================================

    /**
     * Checks if the given username is in the database.
     * @param username the username
     * @return whether the username is in the database
     */
    @RequestMapping(value = {"/searchUser"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean checkUser(@Valid @RequestBody String username) {
        return Database.checkUsername(username);
    }

    /**
     * Getter for a User object from the database.
     * @param token the user's token
     * @return the user
     */
    @RequestMapping(value = {"/getUser"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static User getUser(@Valid @RequestBody String token) {
        return Database.getUser(token);
    }

    /**
     * Adds a friend to the user's friends in the Database.
     * @param friend the friend to add
     * @return whether the friend is added successfully
     */
    @RequestMapping(value = {"/addFriend"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean addFriend(@Valid @RequestBody CompareFriends friend) {
        return Database.addFriend(friend);
    }

    /**
     * Getter for the user's list of friends from the database.
     * @param token the user's token
     * @return the user's list of friends
     */
    @RequestMapping(value = {"/showFriends"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static FriendsList showFriends(@Valid @RequestBody String token) {
        return Database.showFriends(token);
    }

    /**
     * Getter for the user's followers from the database.
     * @param token the user's token
     * @return the user's followers
     */
    @RequestMapping (value = {"/showFollowers"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static FriendsList showFollowers(@Valid @RequestBody String token) {
        return Database.showFollowers(token);
    }

    /**
     * Getter for the leaderboard from the database.
     * @return the leaderboard
     */
    @RequestMapping(value = {"/getLeaderboard"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static FriendsList getLeaderboard() {
        return Database.getLeaderboard();
    }

    /**
     * Getter for the user's OnLoadValues from the database.
     * @param token the user's token.
     * @return the user's OnLoadValues
     */
    @RequestMapping(value = {"/onLoad"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static OnLoadValues onLoad(@Valid @RequestBody String token) {
        OnLoadValues onLoadValues = Database.getOte(token);
        onLoadValues.setUser(Database.getUser(token));
        onLoadValues.setChallenges(new ChallengesList(Database.retrieveChallenges(token)));
        onLoadValues.setFollowers(Database.showFollowers(token).getList().size());
        onLoadValues.setFollowing(Database.showFriends(token).getList().size());
        onLoadValues.setCarbonReduce(Database.getCarbonReduced(token));
        return onLoadValues;
    }

    /**
     * Getter for the user's total CO2 reduction from the database.
     * @param token the user's token.
     * @return the user's total CO2 reduction
     */
    @RequestMapping(value = {"/carbon"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static Action carbon(@Valid @RequestBody String token) {
        return Database.getCarbonValues(token);
    }

    /**
     * Adds a challenge to the user's challenges in the database.
     * @param friend the friend the user challenges
     * @return whether the challenge is successfully added to the database
     */
    @RequestMapping (value = {"/addChallenge"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean addChallenge(@Valid @RequestBody CompareFriends friend) {
        return Database.addChallenge(friend.getToken(), friend.getUsername(), friend.getScore());
    }

    /**
     * Getter for the user's list of challenges from the database.
     * @param token the user's token
     * @return the user's list of challenges
     */
    @RequestMapping (value = {"/getChallenges"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static ChallengesList showChallenges(@Valid @RequestBody String token) {
        return new ChallengesList(Database.retrieveChallenges(token));
    }

    /**
     * Method to accept and initialize a challenge.
     * @param accept the challenge to accept
     * @return the initialized challenge.
     */
    @RequestMapping (value = {"/acceptChallenge"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean acceptChallenge(@Valid @RequestBody Challenge accept) {
        if (accept.isOnA()) {
            return false;
        }
        return Database.initializeChallenge(accept.getUserB(), accept.getUserA());
    }

    /**
     * Getter for the user's recent CO2 reductions from the database.
     * @param token the user's token
     * @return the user's recent CO2 reductions
     */
    @RequestMapping(value = {"/getRecentCOSavings"}, method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public static ActionList getRecentCoSavings(@Valid @RequestBody String token) {
        return new ActionList(Database.getRecentCoSavings(token));
    }
}
