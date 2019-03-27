package client;

/**
 * This class adds an Action to the Database.
 */
public class Action {
    private String user;
    private String action;
    private int    value;
    private int    carbonReduced;
    private int    carbonProduced;
    private long   date;
    
    /**
     * empty constructor
     */
    public Action(){}
    
    /**
     * Constructs and initialises a new instance of Action.
     * @param user   the username
     * @param action the action name
     * @param value  the amount of points
     */
    public Action(String user, String action, int value) {
        this.user = user;
        this.action = action;
        this.value = value;
    }
    
    /**
     * Create a new action.
     * @param user           the user
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon saced
     */
    public Action(String user, String action, int value, int carbonReduced, int carbonProduced) {
        
        this.user = user;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
    }
    
    /**
     * Create a new action.
     * @param user           the user
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon saced
     * @param date           date added (in miliseconds)
     */
    public Action(String user, String action, int value, int carbonReduced, int carbonProduced, long date) {
        this.user = user;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
        this.date = date;
    }
    
    /**
     * Create a new action.
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon saced
     * @param date           date added (in miliseconds)
     */
    public Action(String action, int value, int carbonReduced, int carbonProduced, long date) {
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
        this.date = date;
    }
    
    /**
     * getter for User.
     * @return user
     */
    public String getUser() {
        return user;
    }
    
    /**
     * setter for User.
     */
    public void setUser(String user) {
        this.user = user;
    }
    
    /**
     * getter for Action.
     * @return action
     */
    public String getAction() {
        return action;
    }
    
    /**
     * setter for Action.
     */
    public void setAction(String action) {
        this.action = action;
    }
    
    /**
     * getter for Value.
     * @return value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * setter for Value.
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * getter for carbonReduced.
     * @return carbonReduced
     */
    public int getCarbonReduced() {
        return carbonReduced;
    }
    
    /**
     * setter for carbonReduced.
     */
    public void setCarbonReduced(int carbonReduced) {
        this.carbonReduced = carbonReduced;
    }
    
    /**
     * getter for Value.
     * @return carbonProduced
     */
    public int getCarbonProduced() {
        return carbonProduced;
    }
    
    /**
     * setter for Value.
     */
    public void setCarbonProduced(int carbonProduced) {
        this.carbonProduced = carbonProduced;
    }
    
    /**
     * setter for date.
     */
    public long getDate() {
        return date;
    }
    
    /**
     * getter for date.
     * @return date
     */
    public void setDate(long date) {
        this.date = date;
    }
}
