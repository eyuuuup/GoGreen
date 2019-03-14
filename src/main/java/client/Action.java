package client;

class Action {
    private String user;
    private String action;
    private int    value;

    Action(String user, String action, int value) {
        this.user = user;
        this.action = action;
        this.value = value;
    }

    String getUser() {
        return user;
    }

    void setUser(String user) {
        this.user = user;
    }

    String getAction() {
        return action;
    }

    void setAction(String action) {
        this.action = action;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }
}
