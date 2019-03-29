package server;

import java.util.ArrayList;

/**
 * This class is for the list of actions
 */
public class ActionList {

    ArrayList<Action> list;

    /**
     * Constructs and initializes new instance of the class
     * @param list
     */
    public ActionList(ArrayList<Action> list) {
        this.list = list;
    }

    /**
     * Empty constructor
     */
    public ActionList() {
        this.list = new ArrayList<>();
    }

    /**
     * Getter for the list
     * @return
     */
    public ArrayList<Action> getList() {
        return list;
    }

    /**
     * Setter for the list
     * @param list
     */
    public void setList(ArrayList<Action> list) {
        this.list = list;
    }
}
