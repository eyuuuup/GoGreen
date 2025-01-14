package server;

import org.junit.Test;
import server.holders.CompareFriends;

import static org.junit.Assert.assertEquals;

public class CompareFriendsTest {
    
    private CompareFriends OBJECT = new CompareFriends("ABC", 1230);

    @Test
    public void constructorToken() {
        CompareFriends object = new CompareFriends("test", "user");
        assertEquals(object.getToken(), "test");
        assertEquals(object.getUsername(), "user");
    }

    @Test
    public void constructor() {
        CompareFriends object = new CompareFriends("abc", 123);
        assertEquals(object.getUsername(), "abc");
        assertEquals(object.getScore(), 123);
    }
    
    @Test
    public void constructorEmpty() {
        CompareFriends object = new CompareFriends();
        object.setUsername("A");
        assertEquals(object.getUsername(), "A");
        object.setScore(10);
        assertEquals(object.getScore(), 10);
    }

    @Test
    public void getToken() {
        assertEquals(OBJECT.getToken(), null);
    }

    @Test
    public void setToken() {
        CompareFriends object = new CompareFriends("abc", "test");
        object.setToken("def");
        assertEquals(object.getToken(), "def");
    }


    @Test
    public void getUsername() {
        assertEquals(OBJECT.getUsername(), "ABC");
    }
    
    @Test
    public void setUsername() {
        CompareFriends object = new CompareFriends("abc", 123);
        object.setUsername("def");
        assertEquals(object.getUsername(), "def");
    }
    
    @Test
    public void getScore() {
        assertEquals(OBJECT.getScore(), 1230);
    }
    
    @Test
    public void setScore() {
        CompareFriends object = new CompareFriends("abc", 123);
        object.setScore(456);
        assertEquals(object.getScore(), 456);
    }
    
}
