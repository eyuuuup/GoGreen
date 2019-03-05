package client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Communication {
    private static String username;
    private static String password;
    private static String userID;

    public static boolean addRequest(String action, int points) {
        //if (userID == null) new Exception("Not logged in");

        userID = "testUser";

        Action send = new Action(userID, action, points);
        HttpEntity<client.Action> request = new HttpEntity<>(send);
        //MAKE
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject("http://localhost:8080/addAction", request, String.class);
        //String res = restTemplate.postForObject("http://localhost:8080/addAction", request, String.class);

        System.out.println("result: " + res);

        //if result is positive clearing shit
        send.setAction(null);
        send.setUser(null);
        send.setValue(0);


        return true;
    }

    public static boolean login(String username, String password) {
        //validate if username and password matched those on server
        //if they do retrieve userID and store it
        return true;
    }

    public static boolean register(String username, String password) {

        //send username_ to the server, validate if it is not taken.
        //also send hashed password

        username = username;
        return true;
    }

    public static void main(String[] args) {
        addRequest("testingAction", 1000000);
    }
}