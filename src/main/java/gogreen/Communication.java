package gogreen;

import client.AddAction;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Communication {
    private static String username;
    private static String password;
    private static String userID;

    public static boolean addAction(String action, int points) {
//        if (userID == null) new Exception("Not logged in");

        /*TEST*/ userID = "testUser";

        AddAction send = new AddAction(userID, action, points);
        HttpEntity<client.AddAction> request = new HttpEntity<>(send);

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject("http://145.94.183.170:8080/addAction", request, String.class);
//        String res = restTemplate.postForObject("http://localhost:8080/addAction", request, String.class);

//        System.out.println("result: " + res);
        

        return true;
    }

    public static boolean login(String username_) {
        //validate if username and password matched those on server
        //if they do retrieve userID and store it
        return true;
    }

    public static boolean register(String username_) {
        User.checkName(username_);

        //send username_ to the server, validate if it is not taken.
        //also send hashed password

        username = username_;
        return true;
    }

    public static void main(String[] args) {
        addAction("testingAction", 1000000);
    }
}
