package client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FriendsTest {
    
    private Friends OBJECT = new Friends("ABC", "DEF");
    
    @Test
    public void constructor() {
        Friends object = new Friends("abc", "def");
        assertEquals(object.getToken(), "abc");
        assertEquals(object.getUsername(), "def");
    }
    
    @Test
    public void getToken() {
        assertEquals(OBJECT.getToken(), "ABC");
    }
    
    @Test
    public void setToken() {
        Friends object = new Friends("abc", "def");
        object.setToken("xyz");
        assertEquals(object.getToken(), "xyz");
    }
    
    @Test
    public void getUsername() {
        assertEquals(OBJECT.getUsername(), "DEF");
    }
    
    @Test
    public void setUsername() {
        Friends object = new Friends("abc", "def");
        object.setUsername("xyz");
        assertEquals(object.getUsername(), "xyz");
    }
}