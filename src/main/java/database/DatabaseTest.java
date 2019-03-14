package database;
import org.testng.annotations.Test;
import server.Action;
import server.TokenResponse;
import server.User;


import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DatabaseTest {
    @Test
    public void addAction(){
        Action action = new Action("test", "Ate vegetarian meal", 50);
        assertTrue(Database.addAction(action));
    }

    @Test
    public void silentLogin(){
        assertTrue(Database.silentLoginCheck("test"));
    }

    @Test
    public void register(){
        User user = new User("TestApplication", "admin1");
        String token = "TestApplicationToken";
        Database.register(user, token);
    }

    @Test
    public void checkUsername(){
        assertTrue(Database.checkUsername("test"));
    }

    @Test
    public void checkLogin(){
        User user = new User("test", "admin");
        TokenResponse response = new TokenResponse("test", true);
        assertEquals(Database.checkLogin(user).getToken(), response.getToken());
    }
}
