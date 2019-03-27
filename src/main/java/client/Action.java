package client;

public class Action {
    private String user;
    private String action;
    private int value;
    private int carbonReduced;
    private int carbonProduced;
    /**
     * Constructs and initialises a new instance of Action.
     * @param user the username
     * @param action the action name
     * @param value the amount of points
     * @param carbonReduced amount of carbon saved
     * @param carbonProduced amount of carbon produced
     */
    public Action(String user, String action, int value, int carbonReduced, int carbonProduced) {

        this.user = user;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
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
