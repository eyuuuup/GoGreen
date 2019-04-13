package gogreen.server.holders;

import java.util.ArrayList;

/**
 * This class is for the list of actions.
 */
public class ActionList {

    ArrayList<Action> list;

    /**
     * Constructor for ActionList.
     * @param list list of actions
     */
    public ActionList(ArrayList<Action> list) {
        this.list = list;
    }

    /**
     * Empty constructor.
     */
    public ActionList() {
        this.list = new ArrayList<>();
    }

    /**
     * Getter for the list.
     * @return action list
     */
    public ArrayList<Action> getList() {
        return list;
    }

    /**
     * Setter for the list.
     * @param list action list
     */
    public void setList(ArrayList<Action> list) {
        this.list = list;
    }
}
