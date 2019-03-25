package client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionHistoryTest {
    
    private ActionHistory ACTION = new ActionHistory("ACTION", 100);
    
    @Test
    public void constructor() {
        ActionHistory action = new ActionHistory("action", 10);
        assertEquals(action.getActionName(), "action");
        assertEquals(action.getDate(), 10);
    }
    
    @Test
    public void getAction() {
        assertEquals(ACTION.getActionName(), "ACTION");
    }
    
    @Test
    public void setAction() {
        ActionHistory action = new ActionHistory("action", 10);
        action.setActionName("actionTwo");
        assertEquals(action.getActionName(), "actionTwo");
    }
    
    @Test
    public void getDate() {
        assertEquals(ACTION.getDate(), 100);
    }
    
    @Test
    public void setDate() {
        ActionHistory action = new ActionHistory("action", 10);
        action.setDate(20);
        assertEquals(action.getDate(), 20);
    }
}