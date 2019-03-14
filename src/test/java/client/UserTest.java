package client;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private User USER = new User ("user", "password");
    @Test
    public void constructor() {
        User user = new User("user","password");
        assertEquals(user.getName(),"user");
        assertEquals(user.getPassword(),"password");
    }
    public void getName() { assertEquals(USER.getName(),"user");
    }

    @Test
    public void changeName() {
        User User = new User ("user", "password");
        User.changeName("User");
        assertEquals(User.getName(), "User");
    }

    @Test
    public void getPassword() { assertEquals(USER.getPassword(),"password");
    }

    @Test
    public void changePassword() {
        User User = new User("user", "password");
        User.changePassword("Password");
        assertEquals(User.getPassword(), "Password");
    }
}