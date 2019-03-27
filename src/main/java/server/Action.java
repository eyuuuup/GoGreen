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
     * @param carbonProduced carbon produced
     * @param carbonReduced carbon saced
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
}
