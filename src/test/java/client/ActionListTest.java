package client;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActionListTest {
    
    @Test
    public void constructor() {
        ArrayList<Action> l = new ArrayList<>();
        ActionList al = new ActionList(l);
        assertNotNull(al);
        assertEquals(al.getList(), l);
    }
    
    @Test
    public void constructorEmpty() {
        ActionList al = new ActionList();
        assertNotNull(al);
        assertNotNull(al.getList());
    }
    
    @Test
    public void getList() {
        ArrayList<Action> l = new ArrayList<>();
        ActionList al = new ActionList(l);
        assertEquals(al.getList(), l);
    }
    
    @Test
    public void setList() {
        ArrayList<Action> l = new ArrayList<>();
        ActionList al = new ActionList();
        al.setList(l);
        assertEquals(al.getList(), l);
    }
}