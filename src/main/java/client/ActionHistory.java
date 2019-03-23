package client;

public class ActionHistory {
    private String actionName;
    private long date;
    
    public ActionHistory(String actionName, long date) {
        this.actionName = actionName;
        this.date = date;
    }
    
    public String getActionName() {
        return actionName;
    }
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    public long getDate() {
        return date;
    }
    
    public void setDate(long date) {
        this.date = date;
    }
}
