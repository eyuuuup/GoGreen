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
    public User(String name, int userId)throws IllegalArgumentException{
        if(!checkName(name))throw new IllegalArgumentException("Invalid name!");
        this.name = name;
        this.userId = userId;
        points = 0;
    }

    /**
     * Checks whether a given name is according to the rules
     * @param name
     */
    public boolean checkName(String name){
        if(name == null)return false;
       if(name.length() >= 16 || name.length() <= 0)return false;
       for(int i = 0; i < name.length(); i++){
           int letter = name.charAt(i);
           if(!(letter >= 65 && letter <= 90) && !(letter >= 97 && letter <= 122)) return false;
       }
       return true;
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
    public void changeName(String newName) throws IllegalArgumentException{
        if(!checkName(newName))throw new IllegalArgumentException("Invalid name!");
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
