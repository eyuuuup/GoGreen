package server;

/**
 * This class adds an Action to the Database.
 */
public class Action {
    private String user;
    private String action;
    private int value;

    /**
     * Create a new action.
     * @param user the user
     * @param action the action
     * @param value the value
     */
    public Action(String user, String action, int value) {
        this.user = user;
        this.action = action;
        this.value = value;
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
}
