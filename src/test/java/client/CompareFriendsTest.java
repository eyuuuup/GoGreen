package client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompareFriendsTest {
    
    private CompareFriends OBJECT = new CompareFriends("ABC", 1230);
    
    @Test
    public void constructor() {
        CompareFriends object = new CompareFriends("abc", 123);
        assertEquals(object.getUsername(), "abc");
        assertEquals(object.getScore(), 123);
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