package server.holders;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is for the list of actions.
 */
public class ActionList {

    ArrayList<Action> list;

    /**
     * Constructs and initializes new instance of the class.
     *
     * @param list list
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
     *
     * @return
     */
    public ArrayList<Action> getList() {
        return list;
    }

    /**
     * Setter for the list.
     *
     * @param list list
     */
    public void setList(ArrayList<Action> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionList)) return false;
        ActionList that = (ActionList) o;
        return Objects.equals(list, that.list);
    }

}
