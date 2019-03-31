package server;

import client.Action;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionTest {
    private Action ACTION = new Action("User", "Action", 100,50,50);

    @Test
    public void constructor(){
        Action act= new Action("user", "action", 10, 50,50);
        assertEquals("user", act.getToken());
        assertEquals("action", act.getAction());
        assertEquals(10,act.getValue());
        assertEquals(50, act.getCarbonProduced(), 0);
        assertEquals(50, act.getCarbonReduced(),0);
    }

    @Test
    public void constructorEmpty(){
        Action act= new Action();
        act.setToken("A");
        assertEquals("A", act.getToken());
        act.setAction("action");
        assertEquals("action", act.getAction());
        act.setValue(10);
        assertEquals(10, act.getValue());
        act.setCarbonProduced(50);
        assertEquals(50, act.getCarbonProduced(), 0);

    }

    @Test
    public void getToken() {
    }

    @Test
    public void setToken() {
    }

    @Test
    public void getAction() {
    }

    @Test
    public void setAction() {
    }

    @Test
    public void getValue() {
    }

    @Test
    public void setValue() {
    }

    @Test
    public void getCarbonReduced() {
    }

    @Test
    public void setCarbonReduced() {
    }

    @Test
    public void getCarbonProduced() {
    }

    @Test
    public void setCarbonProduced() {
    }

    @Test
    public void getDate() {
    }

    @Test
    public void setDate() {
    }
}