package client;

public class Action {
    private String user;
    private String action;
    private int    value;

    /**
     * Constructs and initialises a new instance of Action.
     * @param user the username
     * @param action the action name
     * @param value the amount of points
     */
    public Action(String user, String action, int value) {
        this.user = user;
        this.action = action;
        this.value = value;
    }

    public Action(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
