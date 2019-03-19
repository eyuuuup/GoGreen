package server;

public class actionHistory {
    private String actionName;
    private String date;
    private String time;

    public actionHistory(String actionName, String date, String time) {
        this.actionName=actionName;
        this.date=date;
        this.time=time;
    }

    public actionHistory(){}

    public String getActionName() {
        return actionName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
