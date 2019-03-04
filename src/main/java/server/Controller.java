package server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.awt.*;

@RestController
@RequestMapping("/")
public class Controller {

    @RequestMapping(value={"/login"}, method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public String login(@Valid @RequestBody UserDetails u)
    {
//        if(check in database)
//            return "ERWIN";

        return "ERWIN";
    }

    @RequestMapping(value={"/addAction"}, method=RequestMethod.POST)//, produces=MediaType.APPLICATION_JSON_VALUE)
    public String addAction(@Valid @RequestBody AddAction a)
    {

        return "ERWIN";
    }

    @RequestMapping(value={"/newLogin"}, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@Valid @RequestBody UserDetails l)
    {

        return "ERWIN";
    }

    //    public void addAction(AddAction a)
    //    {
    //
    //    }

}

