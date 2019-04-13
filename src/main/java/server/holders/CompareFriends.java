package server.holders;


/**
 * This class represents a friend of the user.
 */
public class CompareFriends {

    private String token;
    private String username;
    private int score;

    /**
     * Constructor for a new friend with the friend's username and the friend's score.
     * @param username username of the friend
     * @param score    score of the friend
     */
    public CompareFriends(String username, int score) {
        this.username = username;
        this.score = score;
        this.token = null;
    }

    /**
     * Constructor for a new friend with the user's token and the friend's username.
     * @param token    token of the user
     * @param username username of the friend
     */
    public CompareFriends(String token, String username) {
        this.token = token;
        this.username = username;
        this.score = 0;
    }

    /**
     * Empty constructor.
     */
    public CompareFriends() {
    }


    /**
     * Getter for the user's token.
     * @return token of the user
     */
    public String getToken() {
        return token;
    }


    /**
     * Setter for the user's token.
     * @param token token of the user
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for the friend's username.
     * @return the friend's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the friend's username.
     * @param username the friend's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the friend's score.
     * @return the friend's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the friend's score.
     * @param score the friend's score
     */
    public void setScore(int score) {
        this.score = score;
    }
}