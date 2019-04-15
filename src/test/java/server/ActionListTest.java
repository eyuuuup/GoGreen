package server;

import org.junit.Test;
import server.holders.Action;
import server.holders.ActionList;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActionListTest {
    
    @Test
    public void constructor() {
        ArrayList<Action> l  = new ArrayList<>();
        ActionList        al = new ActionList(l);
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

    @Test
    public void equals() {
        String fail = "hello";
        ActionList test = new ActionList();
        assertFalse(test.equals(fail));

        Action action= new Action(25,22);
        ArrayList<Action> addAction= new ArrayList<>();
        addAction.add(action);
        ActionList list=new ActionList(addAction);
        assertFalse(list.equals(test));

        ActionList testList = new ActionList(addAction);
        assertTrue(testList.equals(list));
    }
}