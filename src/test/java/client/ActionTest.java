package client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActionTest {
    private Action ACTION = new Action("User", "Action", 100);
    @Test
    public void constructor(){
        Action action = new Action("user", "action", 10);
        assertEquals(action.getUser(), "user");
        assertEquals(action.getAction(), "action");
        assertEquals(action.getValue(), 10);
    }

    @Test
    public void getUser() {
        assertEquals(ACTION.getUser(), "User");
    }

    @Test
    public void setUser() {
        Action action = new Action("user", "action", 10);
        action.setUser("USER");
        assertEquals(action.getUser(), "USER");
    }

    @Test
    public void getAction() {
        assertEquals(ACTION.getAction(), "Action");
    }

    @Test
    public void setAction() {
        Action action = new Action("user", "action", 10);
        action.setAction("ACTION");
        assertEquals(action.getAction(), "ACTION");
    }

    @Test
    public void getValue() {
        assertEquals(ACTION.getValue(), 100);
    }

    @Test
    public void setValue() {
        Action action = new Action("user", "action", 10);
        action.setValue(70);
        assertEquals(action.getValue(), 70);
    }
}