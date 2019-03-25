package server;

import database.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;

@RunWith (PowerMockRunner.class)
@PrepareForTest (Database.class)
public class ControllerTest {
    private User USER = new User("User", "pwd");
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Database.class);
    }
    
    // ========== USER AUTHENTICATION ==========================================
    
    @Test
    public void login() {
        PowerMockito.when(Database.checkLogin(USER)).thenReturn(new TokenResponse("token", true));
        TokenResponse tokenResponse = Controller.login(USER);
        PowerMockito.verifyStatic();
        Database.checkLogin(USER);
        PowerMockito.verifyNoMoreInteractions();
        
        assertEquals(tokenResponse.getToken(), "token");
        assertTrue(tokenResponse.isLegit());
    }
    
    @Test
    public void register() {
        PowerMockito.when(Database.checkUsername(USER.getName())).thenReturn(true);
        TokenResponse tokenResponse = Controller.register(USER);
        PowerMockito.verifyStatic();
        Database.checkUsername(USER.getName());
        
        assertNull(tokenResponse.getToken());
        assertFalse(tokenResponse.isLegit());
    }
    
    @Test
    public void registerIncorrect() {
        PowerMockito.when(Database.checkUsername(USER.getName())).thenReturn(false);
        TokenResponse tokenResponse = Controller.register(USER);
        PowerMockito.verifyStatic();
        Database.checkUsername(USER.getName());
        PowerMockito.verifyNoMoreInteractions();
        
        assertNotNull(tokenResponse.getToken());
        assertTrue(tokenResponse.isLegit());
    }
    
    @Test
    public void silentLoginValid() {
        PowerMockito.when(Database.silentLoginCheck("")).thenReturn(true);
        boolean response = Controller.silentLogin("");
        assertTrue(response);
    }
    
    @Test
    public void silentLoginInvalid() {
        PowerMockito.when(Database.silentLoginCheck("")).thenReturn(false);
        boolean response = Controller.silentLogin("");
        assertFalse(response);
    }
    
    // ========== ACTION HANDLERS ==============================================
    
    @Test
    public void addAction() {
        Action action = new Action("user", "action", 10);
        PowerMockito.when(Database.addAction(action)).thenReturn(true);
        
        boolean bool = Controller.addAction(action);
        PowerMockito.verifyStatic();
        Controller.addAction(action);
        
        assertTrue(bool);
    }
    
    @Test
    public void forDemo() {
        ArrayList<ActionHistory> list = new ArrayList<>();
        list.add(new ActionHistory("testName", 3600));
        PowerMockito.when(Database.retract(anyString())).thenReturn(list);
        
        ArrayList response = Controller.forDemo("testToken");
        PowerMockito.verifyStatic();
        assertEquals(list, response);
    }
    
    @Test
    public void totalScore() {
        PowerMockito.when(Database.getTotalScore(anyString())).thenReturn(150);
        
        int response = Controller.totalScore("testToken");
        PowerMockito.verifyStatic();
        assertEquals(150, response);
    }
    
    // ========== SOCIAL HANDLERS ==============================================
    
    @Test
    public void addFriend() {
        Friends fr = new Friends("testToken", "testFriend");
        
        PowerMockito.when(Database.addFriend(fr)).thenReturn(true);
        
        assertTrue(Controller.addFriend(fr));
    }
    
//    @Test
//    public void showFriends() {
//        ArrayList<CompareFriends> list = new ArrayList<>();
//        list.add(new CompareFriends("testName", 3600));
//        PowerMockito.when(Database.showFriends(anyString())).thenReturn(list);
//
//        assertEquals(list, Controller.showFriends("testToken"));
//    }
    
//    @Test
//    public void showFollowers() {
//        ArrayList<CompareFriends> list = new ArrayList<>();
//        list.add(new CompareFriends("testName", 3600));
//        PowerMockito.when(Database.showFollowers(anyString())).thenReturn(list);
//
//        assertEquals(list, Controller.showFollowers("testToken"));
//    }
}