package goGreen;

/**
 * this class represents an User of the GoGreen Application
 * @author Erwin van Dam
 */
public class User {
    private String name;
    private int userId;
    private int points;

    /**
     * Creates a new User with a name and an id, points is set to 0
     * @param name
     * @param userId
     */
    public User(String name, int userId)throws NullPointerException{
        //don't know what restrictions to do here for the name
        if(name == null)throw new NullPointerException();
        this.name = name;
        this.userId = userId;
        points = 0;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Changes name into newName
     * @param newName
     */
    public void changeName(String newName) throws NullPointerException{
        //don't know what restrictions to do here
        if(newName == null)throw new NullPointerException();
        name = newName;
    }

    /**
     * Getter for userId
     * @return userId
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Getter for points
     * @return points
     */
    public int getPoints(){
        return points;
    }

    /**
     * adds extra points to points
     * @param extraPoints
     */
    public void addPoints(int extraPoints) {
        //don't know what restrictions to apply here, anyone?
        points += extraPoints;
    }
}
