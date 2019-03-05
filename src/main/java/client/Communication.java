package client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Communication {
    private static String userID;

<<<<<<< HEAD:src/main/java/client/Communication.java
    public static boolean addRequest(String action, int points) {
//        if (userID == null) new Exception("Not logged in");
=======
    private static final String host = "http://localhost:8080";
>>>>>>> f7d3c4b9ad595d98101283d2a0e2f83e851dab56:src/main/java/gogreen/Communication.java

    /**
     * Checks whether a given name is according to the rules.
     *
     * @param action the name of the action
     * @param points the value of points to send
     * @return boolean correctly sent to server
     * @throws Exception if not logged in
     */
    public static boolean addAction(String action, int points) {
        if (userID == null) {
            new Exception("Not logged in");
        }

        Action send = new Action(userID, action, points);
        HttpEntity<client.Action> request = new HttpEntity<>(send);
//MAKE
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(host + "/addAction", request, String.class);

        System.out.println("result: " + res);

<<<<<<< HEAD:src/main/java/client/Communication.java
        //if result is positive clearing shit
        send.setAction(null);
        send.setUser(null);
        send.setValue(0);
        
=======
>>>>>>> f7d3c4b9ad595d98101283d2a0e2f83e851dab56:src/main/java/gogreen/Communication.java

        return true;
    }

<<<<<<< HEAD:src/main/java/client/Communication.java
    public static boolean login(String username) {
=======
    /**
     * Checks whether a given username and password matches on the server.
     * If yes, retrieves userID for such combination
     *
     * @param username the username
     * @param password the password
     * @return boolean correctly logged in and userID recieved
     */
    public static boolean login(String username, String password) {
>>>>>>> f7d3c4b9ad595d98101283d2a0e2f83e851dab56:src/main/java/gogreen/Communication.java
        //validate if username and password matched those on server
        //if they do retrieve userID and store it
        return true;
    }

<<<<<<< HEAD:src/main/java/client/Communication.java
    public static boolean register(String username) {
=======
    /**
     * Checks whether a given username is not taken on the server.
     * Stores the username and password, retrieves assigned userID.
     *
     * @param username the username
     * @param password the password
     * @return boolean correctly logged in and userID recieved
     */
    public static boolean register(String username, String password) {
>>>>>>> f7d3c4b9ad595d98101283d2a0e2f83e851dab56:src/main/java/gogreen/Communication.java
        User.checkName(username);

        //send username_ to the server, validate if it is not taken.
        //also send hashed password

<<<<<<< HEAD:src/main/java/client/Communication.java
        username = username;
=======
>>>>>>> f7d3c4b9ad595d98101283d2a0e2f83e851dab56:src/main/java/gogreen/Communication.java
        return true;
    }

    /**
     * Tries to log in with the stored username and password.
     * @return boolean correctly logged in and userID received
     */
    public static boolean silentLogin() {

        //retrieve username and password from somewhere (file)
        //try to log in and retrieve userID

        return true;
    }

    //*TEST_METHOD
    public static void main(String[] args) {
        addRequest("testingAction", 1000000);
    }
    //*/
}
