package server;

import database.Database;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping ("/")
public class Controller {
    
    // ========== USER AUTHENTICATION ==========================================
    
    /**
     * This is the login method which connects the server and client.
     * @param user username, password
     * @return TokenResponse token, bool
     */
    @RequestMapping (value = {"/login"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static TokenResponse login(@Valid @RequestBody User user) {
        //if(check in database)
        return Database.checkLogin(user);
    }
    
    /**
     * Register as new user.
     * checks if username already taken or not and generates new token.
     * if true user is added else false username already exists.
     * @param user username, password
     * @return TokenResponse token,
     */
    @RequestMapping (value = {"/register"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static TokenResponse register(@Valid @RequestBody User user) {
        String  token = null;
        boolean bool  = Database.checkUsername(user.getName());
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
     * don'trequire to enter password.
     * @param token string
     * @return whether token exists
     */
    @RequestMapping (value = {"/silentLogin"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean silentLogin(@Valid @RequestBody String token) {
        return Database.silentLoginCheck(token);
    }
    
    // ========== ACTION HANDLERS ==============================================
    
    /**
     * add action to the database.
     * @param action action
     * @return boolean if action added or not
     */
    @RequestMapping (value = {"/addAction"}, method = RequestMethod.POST)
    public static boolean addAction(@Valid @RequestBody Action action) {
        return Database.addAction(action);
    }
    
    /**
     * For the history.
     * @param token token
     * @return String last three actions
     */
    @RequestMapping (value = {"/retract"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static ActionList forDemo(@Valid @RequestBody String token) {
        return Database.retract(token);
    }
    
    /**
     * returns the total score.
     * @param token token
     * @return int returns the total score
     */
    @RequestMapping (value = {"/getTotalScore"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static int totalScore(@Valid @RequestBody String token) {
        return Database.getTotalScore(token);
    }
    
    // ========== SOCIAL HANDLERS ==============================================
    
    /**
     * check if username searched for following exists or not.
     * @param username username
     * @return boolean is username exists or not
     */
    @RequestMapping (value = {"/searchUser"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean checkUser(@Valid @RequestBody String username) {
        return Database.checkUsername(username);
    }
    
    /**
     * username of the present user.
     * @param token token of the user
     * @return username String
     */
    @RequestMapping (value = {"/getUser"}, method = RequestMethod.GET,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static User getUser(@Valid @RequestBody String token) {
        return Database.getUser(token);
    }
    
    /**
     * adds a friend.
     * @param friend the friend
     * @return boolean
     */
    @RequestMapping (value = {"/addFriend"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean addFriend(@Valid @RequestBody Friends friend) {
        return Database.addFriend(friend);
    }
    
    /**
     * shows the friends.
     * @param token token
     * @return ArrayList of CompareFriends
     */
    @RequestMapping (value = {"/showFriends"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static FriendsList showFriends(@Valid @RequestBody String token) {
        return Database.showFriends(token);
    }
    
    @RequestMapping (value = {"/showFollowers"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static FriendsList showFollowers(@Valid @RequestBody String token) {
        return Database.showFollowers(token);
    }
    
    /**
     * for implementing the leaderboard and getting top ten users.
     * @return returns a list of top ten users
     */
    @RequestMapping (value = {"/getLeaderboard"}, method = RequestMethod.GET,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static FriendsList getLeaderboard() {
        return Database.getLeaderboard();
    }

    @RequestMapping(value={"/carbon"}, method=RequestMethod.POST,
                    produces= MediaType.APPLICATION_JSON_VALUE)
    public static Action carbon(@Valid @RequestBody String token){
        return new Action( Database.getCarbonProduced(token), Database.getCarbonReduced(token));
    }
}
