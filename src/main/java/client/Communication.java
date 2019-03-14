package client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootApplication
public class Communication {
    private static final String hostURL = "http://localhost:8080";
    private static final String fileDir = "src/extraFiles/Token.txt";
    private static       String token   = null;

    private Communication() {}

    /**
     * Checks whether a given username is not taken on the server.
     * Stores the username and password, retrieves assigned token.
     * @param username the username
     * @param password the password
     * @param remember whether to store token in a file
     * @return boolean correctly logged in and token recieved
     */
    public static boolean register(String username, String password, boolean remember) {
        //send username and password to the server, validate if it is not taken
        //expect generated token if successful, null if username taken
        return submit(username, password, remember, "/register");
    }

    /**
     * Checks whether a given username and password matches on the server.
     * If yes, retrieves token for such combination for further authentication
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
     * Handles login and register.
     * avoid duplicate code.
     * @param username the username
     * @param password the password
     * @param remember wether to store token in a file
     * @param postUrl determine between login and register
     * @return boolean wether the submit/fetch was sucessful
     */
    private static boolean submit(
            String username, String password, boolean remember, String postUrl) {
        User             user    = new User(username, password);
        HttpEntity<User> message = new HttpEntity<>(user);

        RestTemplate  request  = new RestTemplate();
        TokenResponse response =
                request.postForObject(hostURL + postUrl, message, TokenResponse.class);

        if (!response.isLegit()) {
            System.out.println("not found");
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

        return true;
    }

    /**
     * Removes traces of previous user
     */
    public static void logout() {
        // remove token from Main Memory
        token = null;

        // remove token from Secondary Memory
        File file = new File(fileDir);
        file.delete();
    }

    /**
     * Checks wether the user is logged in, checked by comparing token to null
     *
     * @return wether the user is logged in
     */
    private static boolean isLoggedIn() {
        return token != null;
    }

    /**
     * Checks whether a given name is according to the rules.
     * @param actionName the name of the action
     * @param points     the value of points to send
     * @return boolean correctly sent to server
     */
    public static boolean addAction(String actionName, int points) {
        if (!isLoggedIn()) {
            return false; // not logged in
        }

        Action                    action  = new Action(token, actionName, points);
        HttpEntity<client.Action> message = new HttpEntity<>(action);

        RestTemplate request  = new RestTemplate();
        return request.postForObject(hostURL + "/addAction", message, boolean.class);
    }

    /**
     * Sends request to the server to retrieve last three actions for current user.
     *
     * @return string containing last three actions
     */
    public static String getLastThreeActions() {
        if (!isLoggedIn()) {
            return null; // not logged in
        }

        HttpEntity<String> message = new HttpEntity<>(token);

        RestTemplate request  = new RestTemplate();

        return request.postForObject(hostURL + "/retract", message, String.class);
    }

}
