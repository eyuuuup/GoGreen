package client;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FriendsListTest {
    
    @Test
    public void constructor() {
        FriendsList fl = new FriendsList(new ArrayList<>());
        assertNotNull(fl);
        assertNotNull(fl.getList());
    }
    
    @Test
    public void constructorEmpty() {
        FriendsList fl = new FriendsList();
        assertNotNull(fl);
        assertNotNull(fl.getList());
    }
    
    @Test
    public void getList() {
        FriendsList fl = new FriendsList();
        assertNotNull(fl);
        assertNotNull(fl.getList());
    }
    
    @Test
    public void setList() {
        FriendsList               fl   = new FriendsList();
        ArrayList<CompareFriends> list = new ArrayList<>();
        fl.setList(list);
        assertEquals(list, fl.getList());
    }
}