package server.holders;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents a list of actions.
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

    /**
     * Equals method for ActionList.
     * @param other other object
     * @return whether this equals the other object
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ActionList)) {
            return false;
        }
        ActionList that = (ActionList) other;
        return Objects.equals(list, that.list);
    }

}
