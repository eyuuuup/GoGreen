package client;

/**
 * This class is for getting username and score of a user's friend
 */
public class CompareFriends {


    private String token;
    private String username;
    private int score;

    /**
     * Create a new friend object
     *
     * @param username
     * @param score
     */
    public CompareFriends(String username, int score) {
        this.username = username;
        this.score = score;
        this.token = null;
    }

    /**
     * creates a new friend object
     *
     * @param token
     * @param username
     */
    public CompareFriends(String token, String username) {
        this.token = token;
        this.username = username;
        this.score = 0;
    }

    /**
     * Empty constructor
     */
    public CompareFriends() {
    }


    /**
     * getter for token of the user
     *
     * @return
     */
    public String getToken() {
        return token;
    }


    /**
     * setter for the user's token
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * getter for the username of a friend
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for the username of a friend
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for the total score of a fiend
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * setter for the total score of friend
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
}