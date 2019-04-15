package server.holders;

/**
 * This class represents an Action on the client side.
 */
public class Action {

    private String token;
    private String action;
    private int value;
    private double carbonReduced;
    private double carbonProduced;
    private long date;
    private String description;

    /**
     * Empty constructor for Action.
     */
    public Action() {
    }

    /**
     * Constructor for Action containing only the carbon values.
     * @param carbonProduced kilograms of carbon produced
     * @param carbonReduced  kilograms of carbon reduced
     */
    public Action(double carbonProduced, double carbonReduced) {
        this.carbonProduced = carbonProduced;
        this.carbonReduced = carbonReduced;
    }

    /**
     * Constructor for Action containing token, action name and the amount of points.
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
     * Constructor for Action containing token, action name, the amount of points,
     * the amount of carbon produced and the amount of carbon reduced.
     * @param token          the token
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon reduced
     */
    public Action(String token, String action, int value, double carbonReduced,
                  double carbonProduced) {
        this.token = token;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
    }

    /**
     * Constructor for Action containing token, action name, the amount of points,
     * the amount of carbon produced, the amount of carbon reduced and the date of the action.
     * @param token          the token
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon reduced
     * @param date           date added (in milliseconds)
     */
    public Action(String token, String action, int value, double carbonReduced,
                  double carbonProduced, long date) {
        this.token = token;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
        this.date = date;
    }

    /**
     * Constructor for Action containing token, action name, the amount of points,
     * the amount of carbon produced and the description.
     * @param token          the token
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param description    the description
     */
    public Action(String token, String action, int value, double carbonReduced,
                  double carbonProduced, String description) {
        this.token = token;
        this.action = action;
        this.value = value;
        this.carbonReduced = carbonReduced;
        this.carbonProduced = carbonProduced;
        this.description = description;
    }

    /**
     * Constructor for Action containing action name, the amount of points,
     * the amount of carbon produced, the amount of carbon reduced and the date of the action.
     * @param action         the action
     * @param value          the value
     * @param carbonProduced carbon produced
     * @param carbonReduced  carbon reduced
     * @param date           date added (in milliseconds)
     */
    public Action(String action, int value, double carbonReduced,
                  double carbonProduced, long date) {
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
     * @param token token
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
     * @param action action name
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
     * @param value value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * getter for carbonReduced.
     * @return carbonReduced
     */
    public double getCarbonReduced() {
        return carbonReduced;
    }

    /**
     * setter for carbonReduced.
     * @param carbonReduced carbonReduced
     */
    public void setCarbonReduced(double carbonReduced) {
        this.carbonReduced = carbonReduced;
    }

    /**
     * getter for carbonProduced.
     * @return carbonProduced
     */
    public double getCarbonProduced() {
        return carbonProduced;
    }

    /**
     * setter for carbonProduced.
     * @param carbonProduced carbon Produced
     */
    public void setCarbonProduced(double carbonProduced) {
        this.carbonProduced = carbonProduced;
    }

    /**
     * getter for date.
     * @return date
     */
    public long getDate() {
        return date;
    }

    /**
     * setter for date.
     * @param date date
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * getter for description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description.
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
