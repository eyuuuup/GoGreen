package server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class Controller {

    /**
     * This is the login method which connects the server and client.
     * @param userDetails user details
     * @return login successful
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@Valid @RequestBody UserDetails userDetails) {
        //if(check in database)
        //return "Erwin"
        return "ERWIN";
    }

    //, produces=MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = {"/addAction"}, method = RequestMethod.POST)
    public String addAction(@Valid @RequestBody AddAction addAction) {
        return "ERWIN";
    }

    @RequestMapping(value = {"/newLogin"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@Valid @RequestBody UserDetails userDetails) {

        return "ERWIN";
    }

    //    public void addAction(AddAction a)
    //    {
    //
    //    }

}

