package server;

import java.util.ArrayList;

/**
 * for the list of actionsHistory
 */
public class ActionList {

    ArrayList<Action> list;
    
    public ActionList(ArrayList<Action> list) {
        this.list = list;
    }
    
    public ActionList() {
        this.list = new ArrayList<>();
    }
    
    public ArrayList<Action> getList() {
        return list;
    }
    
    public void setList(ArrayList<Action> list) {
        this.list = list;
    }
}
