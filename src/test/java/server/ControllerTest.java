package server;

import database.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Database.class)
public class ControllerTest {
    private User USER = new User("User", "pwd");

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Database.class);
    }

    // ========== USER AUTHENTICATION ==========================================

    @Test
    public void controller() {
        Controller c = new Controller();
    }

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
        Action action = new Action("user", "action", 10, 50, 50);
        PowerMockito.when(Database.addAction(action)).thenReturn(true);

        boolean bool = Controller.addAction(action);
        PowerMockito.verifyStatic();
        Controller.addAction(action);

        assertTrue(bool);
    }

    @Test
    public void forDemo() {
        ActionList list = new ActionList();
        PowerMockito.when(Database.retract(anyString())).thenReturn(list);

        ActionList response = Controller.forDemo("testToken");
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
    public void checkUser() {
        String username = "testFriend";

        PowerMockito.when(Database.checkUsername(username)).thenReturn(true);

        assertTrue(Controller.checkUser(username));
    }


    @Test
    public void getUser() {
        String username = "testFriend";

        User user = new User();

        PowerMockito.when(Database.getUser(username)).thenReturn(user);

        assertEquals(user, Controller.getUser(username));
    }

    @Test
    public void addFriend() {
        CompareFriends fr = new CompareFriends("testToken", "testFriend");

        PowerMockito.when(Database.addFriend(fr)).thenReturn(true);

        assertTrue(Controller.addFriend(fr));
    }

    @Test
    public void showFriends() {
        FriendsList fl = new FriendsList();
        PowerMockito.when(Database.showFriends(anyString())).thenReturn(fl);

        assertEquals(fl, Controller.showFriends("testToken"));
    }

    @Test
    public void showFollowers() {
        FriendsList fl = new FriendsList();
        PowerMockito.when(Database.showFollowers(anyString())).thenReturn(fl);

        assertEquals(fl, Controller.showFollowers("testToken"));
    }

    @Test
    public void getLeaderboard() {
        FriendsList fl = new FriendsList();
        PowerMockito.when(Database.getLeaderboard()).thenReturn(fl);

        assertEquals(fl, Controller.getLeaderboard());
    }

    @Test
    public void carbon() {
        String token = "testUser";

        PowerMockito.when(Database.getCarbonValues(anyString())).thenReturn(new Action(50, 50));
        Action response = Controller.carbon("testUser");
        assertEquals(50, response.getCarbonProduced(), 0.01);
        assertEquals(50, response.getCarbonReduced(), 0.01);

    }

//    @Test
//    public void onLoadValues() {
//        String token = "user";
//
//        OnLoadValues ol = new OnLoadValues();
//        PowerMockito.when(Database.oneTimeEvent(token)).thenReturn(ol);
//
//        assertEquals(ol, Controller.onLoad(token));
//    }

    @Test
    public void addChallenger() {
        CompareFriends fr = new CompareFriends("test", "testuser");

        PowerMockito.when(Database.addChallenge(fr)).thenReturn(true);

        assertTrue(Controller.addCahllenge(fr));
    }

    @Test
    public void acceptChallenge() {
        CompareFriends fr = new CompareFriends("test", "testuser");

        PowerMockito.when(Database.initializeChallenge(fr)).thenReturn(true);
        assertEquals(true, Controller.acceptChallenge(fr));
    }

    @Test
    public void showChallenges() {
        ChallengesList cl = new ChallengesList();

        PowerMockito.when(Database.retrieveChallenge(anyString())).thenReturn(cl);

        assertEquals(cl, Controller.showChallenges("testToken"));
    }

    @Test
    public void updateChallenge() {
        PowerMockito.when(Database.updateChallenge(anyString())).thenReturn(true);
        assertEquals(Controller.updateChallenge("testToken"), true);
    }

}