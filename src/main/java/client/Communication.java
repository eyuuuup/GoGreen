package client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static gogreen.Application.checkName;

@SpringBootApplication
public class Communication {
    private static String token;

    private static final String hostDir = "http://localhost:8080";
    private static final String fileDir = "src/docs/files/token.txt";

    /**
     * Checks whether a given name is according to the rules.
     *
     * @param actionName the name of the action
     * @param points     the value of points to send
     * @return boolean correctly sent to server
     */
    public static boolean addAction(String actionName, int points) {
        if (token == null) {
            return false; // not logged in
        }

        Action                    action  = new Action(token, actionName, points);
        HttpEntity<client.Action> message = new HttpEntity<>(action);

        RestTemplate request  = new RestTemplate();
        boolean      response = request.postForObject(hostDir + "/addAction", message, boolean.class);

        return response;
    }

    /**
     * Checks whether a given username and password matches on the server.
     * If yes, retrieves token for such combination for further authentication
     *
     * @param username the username
     * @param password the password
     * @return boolean correctly logged in and token recieved
     */
    public static boolean login(String username, String password, boolean remember) {

        //validate if username and password matched those on server
        //if they do retrieve token and store it

        return true;
    }

    /**
     * Checks whether a given username is not taken on the server.
     * Stores the username and password, retrieves assigned token.
     *
     * @param username the username
     * @param password the password
     * @return boolean correctly logged in and token recieved
     */
    public static boolean register(String username, String password, boolean remember) {

        gogreen.GoGreenApplication.checkName(username);

        //send username_ to the server, validate if it is not taken.
        //also send hashed password

        return true;
    }

    /**
     * Tries to log in with the stored username and password.
     *
     * @return boolean correctly logged in and token received
     */
    public static boolean silentLogin() {

        //retrieve username and password from somewhere (file)
        //try to log in and retrieve token

        return false;
    }

    /*  TEST_METHOD
    public static void main(String[] args) {
        addAction("testingAction", 1000000);
    }
    //*/
}
