package server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID; //LEAVE ONE LINE
import javax.validation.Valid;


@RestController
@RequestMapping("/")
public class Controller {
    /**
     * This is the login method which connects the server and client.
     * @param user username, password
     * @return TokenResponse token, bool
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenResponse login(@Valid @RequestBody User user) {
        //if(check in database)
        TokenResponse token = ReplaceByDatabaseMethods.checkLogin(user);
        return token;
    }

    /**
     * Register as new user.
     * checks if username already taken or not and generates new token
     * @param user username, passsword
     * @return TokenResponse token, bool
     * if true user is added else false username already exists
     */
    @RequestMapping(value = {"/register"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenResponse register(User user) {
        String token = null;
        boolean bool=ReplaceByDatabaseMethods.checkUsername(user.getName());
        if (bool == true) {
            //generate TOKEN
            token = UUID.randomUUID().toString();
            ReplaceByDatabaseMethods.setNewUser(user,token);
            return new TokenResponse(token, true);
         } else {
            return new TokenResponse(token, false);
        }
    }

    /**
     * don'trequire to enter password.
     * @param token string
     * @return
     */
    @RequestMapping(value = {"/silentLogin"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String silentLogin(@Valid @RequestBody String token) {

        //IMPLEMENT
        return "ERWIN";
    }

    @RequestMapping(value = {"/addAction"}, method = RequestMethod.POST)
    public boolean addAction(@Valid @RequestBody AddAction addAction) {
        boolean bool = ReplaceByDatabaseMethods.addAction(addAction);
        return bool;
    }


}

