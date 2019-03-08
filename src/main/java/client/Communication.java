package client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;

import static gogreen.Application.checkName;

@SpringBootApplication
public class Communication {
    private static String token = null;

    private static final String hostURL = "http://localhost:8080";
    private static final String fileDir = "src/extraFiles/token.txt";

    /**
     * Checks whether a given username is not taken on the server.
     * Stores the username and password, retrieves assigned token.
     *
     * @param username the username
     * @param password the password
     * @param remember wether to store token in a file
     * @return boolean correctly logged in and token recieved
     */
    public static boolean register(String username, String password, boolean remember) {
        //send username and password to the server, validate if it is not taken
        //expect generated token if successfull, null if username taken
        return submit(username, password, remember, "/register");
    }

    /**
     * Checks whether a given username and password matches on the server.
     * If yes, retrieves token for such combination for further authentication
     *
     * @param username the username
     * @param password the password
     * @param remember wether to store token in a file
     * @return boolean correctly logged in and token recieved
     */
    public static boolean login(String username, String password, boolean remember) {
        //validate if username and password matched those on server
        //if they do retrieve token and store it
        return submit(username, password, remember, "/login");
    }

    /**
     * Handles login and register
     * avoid duplicate code
     *
     * @param username the username
     * @param password the password
     * @param remember wether to store token in a file
     * @param postURL determine between login and register
     * @return boolean wether the submit/fetch was sucessfull
     */
    private static boolean submit(String username, String password, boolean remember, String postURL) {
        if (!checkName(username)) {
            // username contains prohibited characters
            return false;
        }

        User             user    = new User(username, password);
        HttpEntity<User> message = new HttpEntity<>(user);

        RestTemplate  request  = new RestTemplate();
        TokenResponse response = request.postForObject(hostURL + postURL, message, TokenResponse.class);

        if (!response.isLegit()) {
            // not matching login and password
            return false;
        }

        token = response.getToken();

        try {
            FileWriter out = new FileWriter(new File(fileDir));
            if (remember) {
                //save token to file
                out.write(token);
            } // else over-write perhaps stored token
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        token = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(fileDir));
            token = in.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

//        Walidate on Server
//        HttpEntity<TokenResponse> message = new HttpEntity<>(new TokenResponse(token, true));
//
//        RestTemplate request  = new RestTemplate();
//        boolean      response = request.postForObject(hostURL + "/silentLogin", message, boolean.class);
//
//        return response;

        return true;
    }

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
        boolean      response = request.postForObject(hostURL + "/addAction", message, boolean.class);

        return response;
    }

    /*  TEST_METHOD
    public static void main(String[] args) {
        addAction("testingAction", 1000000);
    }
    //*/
}
