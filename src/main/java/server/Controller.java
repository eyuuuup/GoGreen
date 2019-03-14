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
     * @return String
     */
    @RequestMapping (value = {"/silentLogin"}, method = RequestMethod.POST,
                     produces = MediaType.APPLICATION_JSON_VALUE)
    public static String silentLogin(@Valid @RequestBody String token) {
        Database.silentLoginCheck(token);
        //IMPLEMENT
        return "ERWIN";
    }

    @RequestMapping (value = {"/addAction"}, method = RequestMethod.POST)
    public static boolean addAction(@Valid @RequestBody Action action) {
        return Database.addAction(action);
    }

    @RequestMapping (value = {"/retract"}, method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public static String forDemo(@Valid @RequestBody String token) {
        return Database.retract(token);
    }

}

