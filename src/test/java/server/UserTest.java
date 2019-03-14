package server;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private User USER = new User("Username", "Password");
    @Test
    public void constructor(){
        User user = new User("Username", "password");
        assertEquals(user.getName(), "Username");
        assertEquals(user.getPassword(), "password");
    }

    @Test
    public void getName() {
        assertEquals(USER.getName(), "Username");
    }

    @Test
    public void getPassword() {
        assertEquals(USER.getPassword(), "Password");
    }
}