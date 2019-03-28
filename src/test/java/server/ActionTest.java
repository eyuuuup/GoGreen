package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionTest {
    private Action ACTION = new Action("User", "Action", 100,50,50);
    
    @Test
    public void constructor(){
        Action action = new Action("user", "action", 10,50,50);
        assertEquals(action.getUser(), "user");
        assertEquals(action.getAction(), "action");
        assertEquals(action.getValue(), 10);
        assertEquals(action.getCarbonProduced(), 50);
        assertEquals(action.getCarbonReduced(), 50);
    }
    
    @Test
    public void constructorEmpty() {
        Action action = new Action();
        action.setUser("A");
        assertEquals(action.getUser(), "A");
        action.setAction("A");
        assertEquals(action.getAction(), "A");
        action.setValue(10);
        assertEquals(action.getValue(), 10);
        action.setCarbonProduced(50);
        assertEquals(action.getCarbonProduced(), 50);
        action.setCarbonReduced(50);
        assertEquals(action.getCarbonProduced(), 50);
    }
    
    @Test
    public void constructorClient() {
        Action action = new Action("user", "action", 25);
        assertEquals(action.getUser(), "user");
        assertEquals(action.getAction(), "action");
        assertEquals(action.getValue(), 25);
    }
    
    @Test
    public void constructorClientPlus() {
        Action action = new Action("user", "action", 25, 100, 250, 15000);
        assertEquals(action.getUser(), "user");
        assertEquals(action.getAction(), "action");
        assertEquals(action.getValue(), 25);
        assertEquals(action.getCarbonReduced(), 100);
        assertEquals(action.getCarbonProduced(), 250);
        assertEquals(action.getDate(), 15000);
    }
    
    @Test
    public void constructorServer() {
        Action action = new Action("action", 25, 100, 250, 15000);
        assertEquals(action.getAction(), "action");
        assertEquals(action.getValue(), 25);
        assertEquals(action.getCarbonReduced(), 100);
        assertEquals(action.getCarbonProduced(), 250);
        assertEquals(action.getDate(), 15000);
    }
    
    @Test
    public void getUser() {
        assertEquals(ACTION.getUser(), "User");
    }
    
    @Test
    public void setUser() {
        Action action = new Action("user", "action", 10,50,50);
        action.setUser("USER");
        assertEquals(action.getUser(), "USER");
    }
    
    @Test
    public void getAction() {
        assertEquals(ACTION.getAction(), "Action");
    }
    
    @Test
    public void setAction() {
        Action action = new Action("user", "action", 10,50,50);
        action.setAction("ACTION");
        assertEquals(action.getAction(), "ACTION");
    }
    
    @Test
    public void getValue() {
        assertEquals(ACTION.getValue(), 100);
    }
    
    @Test
    public void setValue() {
        Action action = new Action("user", "action", 10,50,50);
        action.setValue(70);
        assertEquals(action.getValue(), 70);
    }
    
    @Test
    public void setDate(){
        Action action = new Action();
        action.setDate(15000);
        assertEquals(action.getDate(), 15000);
    }
}