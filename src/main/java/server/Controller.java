package server;

import database.Database;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import javax.validation.Valid;

@RestController
@RequestMapping ("/")
public class Controller {
    private Controller() {
    }

    /**
     * This is the login method which connects the server and client.
     *
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
     *
     * @param user username, passsword
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
     *
     * @param token string
     * @return whether token exists
     */
    @RequestMapping (value = {"/silentLogin"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean silentLogin(@Valid @RequestBody String token) {
        return Database.silentLoginCheck(token);
    }

    /**
     * add action to the database
     * @param action
     * @return boolean if action added or not
     */
    @RequestMapping (value = {"/addAction"}, method = RequestMethod.POST)
    public static boolean addAction(@Valid @RequestBody Action action) {
        return Database.addAction(action);
    }

    /**
     * For the history
     *
     * @param token
     * @return String last three actions
     */
    @RequestMapping (value = {"/retract"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static ArrayList<actionHistory> forDemo(@Valid @RequestBody String token) {
        return Database.retract(token);
    }

    /**
     *
     * @param token
     * @return int returns the total score
     */
    @RequestMapping(value={"/getTotalScore"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static int totalScore(@Valid @RequestBody String token)
    {
        int score=0;
        score= Database.getTotalScore(token);
        return score;
    }

    /**
     *
     * @param friend
     * @return boolean
     */
    @RequestMapping(value={"/addFriend"}, method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean addFriend(@Valid @RequestBody Friends friend)
    {
        return Database.addFriend(friend);
    }

    /**
     *
     * @param token
     * @return ArrayList of CompareFriends
     */
    @RequestMapping(value={"/showFriends"}, method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static ArrayList<CompareFriends> showFriends(@Valid @RequestBody String token)
    {
        return Database.showFriends(token);
    }

    @RequestMapping(value={"/showFollowers"}, method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static ArrayList<CompareFriends> showFollowers(@Valid @RequestBody String token)
    {
        return Database.showFollowers(token);
    }


}

