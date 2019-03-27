package server;

/**
 * This class adds an Action to the Database.
 */
public class Action {
    private String user;
    private String action;
    private int value;
    private int carbonReduced;
    private int carbonProduced;
    /**
     * Create a new action.
     * @param user the user
     * @param action the action
     * @param value the value
     */
    public Action(String user, String action, int value, int carbonReduced, int carbonProduced) {
        this.user = user;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
    }

    /**
     * getter for User.
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for Action.
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * getter for Value.
     * @return value
     */
    public int getValue() {
        return value;
    }


    public int getCarbonReduced() {
        return carbonReduced;
    }

    public void setCarbonReduced(int carbonReduced) {
        this.carbonReduced = carbonReduced;
    }

    public int getCarbonProduced() {
        return carbonProduced;
    }

    public void setCarbonProduced(int carbonProduced) {
        this.carbonProduced = carbonProduced;
    }
}
