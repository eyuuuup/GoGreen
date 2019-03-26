package server;

/**
 * this class represents an User of the gogreen Application.
 * @author Erwin van Dam
 */
public class User {
    private String name;
    private String password;
    private String email;
    private int    totalScore;
    
    public User(String name, String password, String email, int totalScore) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.totalScore = totalScore;
    }
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public User() {
    }
    
    /**
     * Getter for name.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Changes name into newName.
     * @param newName the new name
     */
    public void setName(String newName) {
        name = newName;
    }
    
    /**
     * Getter for password.
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Changes name into new password.
     * @param newPassword the new name
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
