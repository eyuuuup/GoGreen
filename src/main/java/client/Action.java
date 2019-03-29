package client;

/**
 * This class adds an Action to the Database.
 */
public class Action {
    private String token;
    private String action;
    private int    value;
    private int    carbonReduced;
    private int    carbonProduced;
    private long   date;
    
    /**
     * empty constructor
     */
    public Action() {
    }
    
    /**
     * Constructs and initialises a new instance of Action.
     * @param token  the token
     * @param action the action name
     * @param value  the amount of points
     */
    public Action(String token, String action, int value) {
        this.token = token;
        this.action = action;
        this.value = value;
    }
    
    /**
     * Create a new action.
     * @param token          the token
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon saced
     */
    public Action(String token, String action, int value, int carbonReduced, int carbonProduced) {
        
        this.token = token;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
    }
    
    /**
     * Create a new action.
     * @param token          the token
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon saced
     * @param date           date added (in miliseconds)
     */
    public Action(String token, String action, int value, int carbonReduced, int carbonProduced, long date) {
        this.token = token;
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
     * getter for token.
     * @return token
     */
    public String getToken() {
        return token;
    }
    
    /**
     * setter for token.
     */
    public void setToken(String token) {
        this.token = token;
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
