package server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.UUID;

import static server.ReplaceByDatabaseMethods.*;

@RestController
@RequestMapping("/")
public class Controller {

//    /**
//     * This is the login method which connects the server and client.
//     * @param userDetails username, password
//     * @return TokenResponse token, bool
//     */
//    @RequestMapping(value = {"/login"}, method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public TokenResponse login(@Valid @RequestBody UserDetails userDetails) {
//        //if(check in database)
//        TokenResponse t= checkLogin(userDetails);
//        return t;
//    }

    /**
     * Register as new user
     * checks if username already taken or not and generates new token
     * @param userDetails username, passsword
     * @return TokenResponse token, bool
     * true user set
     * false username already exists
     */
//    @RequestMapping(value={"/register"}, method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public TokenResponse register(UserDetails userDetails)
//    {
//        String token=null;
//        boolean bool=checkUsername(userDetails.getUsername());
//        if(bool==true)
//        {
//            //generate TOKEN
//            token = UUID.randomUUID().toString();
//            setNewUser(userDetails,token);
//            return new TokenResponse(token, true);
//        }
//
//        else
//        {
//            return new TokenResponse(token, false);
//        }
//    }

    /**
     * don'trequire to enter password
     * @param token
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
        boolean bool =ReplaceByDatabaseMethods.addAction(addAction);
        return bool;
    }


}

