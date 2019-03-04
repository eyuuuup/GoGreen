package server;

public class AddAction {
    private String user;
    private String action;
    private int value;

    public AddAction(String user, String action, int value)
    {
        this.user=user;
        this.action=action;
        this.value=value;
    }

    public String getUser()
    {
        return user;
    }

    public String getAction()
    {
        return action;
    }

    public int getValue()
    {
        return value;
    }
}
