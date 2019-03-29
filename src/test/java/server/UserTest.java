package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTest {
    private User USER = new User("user","password", "mail", 150);
    
    @Test
    public void constructorEmpty() {
        User user = new User();
        assertNotNull(user);
    }
    
    @Test
    public void constructorOne() {
        User user = new User("user","password");
        assertEquals(user.getName(),"user");
        assertEquals(user.getPassword(),"password");
    }
    
    @Test
    public void constructorTwo() {
        User user = new User("user","password", "mail", 150);
        assertEquals(user.getName(),"user");
        assertEquals(user.getPassword(),"password");
        assertEquals(user.getEmail(),"mail");
        assertEquals(user.getTotalScore(),150);
    }
    
    public void getName() { assertEquals(USER.getName(),"user");
    }

    @Test
    public void changeName() {
        User User = new User ("user", "password");
        User.setName("User");
        assertEquals(User.getName(), "User");
    }

    @Test
    public void getPassword() { assertEquals(USER.getPassword(),"password");
    }

    @Test
    public void changePassword() {
        User User = new User("user", "password");
        User.setPassword("Password");
        assertEquals(User.getPassword(), "Password");
    }
    @Test
    public void getEmail() {
        assertEquals(USER.getEmail(), "mail");
    }
    
    @Test
    public void setEmail() {
        User user = new User("user","password", "mail", 150);
        user.setEmail("newmail");
        assertEquals(user.getEmail(), "newmail");
    }
    
    @Test
    public void getTotalScore() {
        assertEquals(USER.getTotalScore(), 150);
    }
    
    @Test
    public void setTotalScore(){
        User user = new User("user","password", "mail", 150);
        user.setTotalScore(250);
        assertEquals(user.getTotalScore(), 250);
        
    }
}