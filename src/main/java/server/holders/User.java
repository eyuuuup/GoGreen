package server.holders;

/**
 * This class represents an User of the Application.
 */
public class User {

    private String name;
    private String password;
    private String email;
    private int totalScore;

    /**
     * Constructor for User with the user's name, the user's password,
     * the user's email address and the user's total score.
     * @param name       the user's name (encoded)
     * @param password   the user's password (hashed)
     * @param email      the user's email address
     * @param totalScore the user's total score
     */
    public User(String name, String password, String email, int totalScore) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.totalScore = totalScore;
    }

    /**
     * Constructor for User with the user's name and the user's password.
     * @param name     the user's name (encoded)
     * @param password the user's password (hashed)
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Empty User constructor.
     */
    public User() {
    }

    /**
     * Getter for the user's name.
     * @return the user's name (encoded)
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the user's name.
     * @param newName the user's name (encoded)
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Getter for the user's password.
     * @return the user's password (hashed)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password.
     * @param newPassword the user's password (hashed)
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Getter for the user's email address.
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the user's email address.
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the user's total score.
     * @return the user's total score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Setter for the user's total score.
     * @param totalScore the user's total score
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
