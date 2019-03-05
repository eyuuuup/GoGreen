package client;

public class Action {

    private String user;
    private String action;
    private int    value;

<<<<<<< HEAD:src/main/java/client/Action.java
    public Action(String user, String action, int value) {
=======
    /**
     * Creates a new Action.
     * @param user the user
     * @param action the action
     * @param value the value
     */
    public AddAction(String user, String action, int value) {
>>>>>>> f7d3c4b9ad595d98101283d2a0e2f83e851dab56:src/main/java/client/AddAction.java
        this.user = user;
        this.action = action;
        this.value = value;
    }

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

    @Override
    public String toString() {
        return "<AddAction(user=\"" + user + "\", action=\""
                + action + "\", value=\"" + value + "\")>";
    }
}
