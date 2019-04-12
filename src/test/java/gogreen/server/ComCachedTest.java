package gogreen.server;

import gogreen.server.holders.Action;
import gogreen.server.holders.CompareFriends;
import gogreen.server.holders.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith (PowerMockRunner.class)
@PrepareForTest ({Communication.class})
public class ComCachedTest {

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(Communication.class);
    }

    @Test
    public void register() {
        when(Communication.register("a", "b", true)).thenReturn(true);
        assertTrue(ComCached.register("a", "b", true));
    }

    @Test
    public void login() {
        when(Communication.login("a", "b", true)).thenReturn(true);
        assertTrue(ComCached.login("a", "b", true));
    }

    @Test
    public void silentLogin() {
        when(Communication.silentLogin()).thenReturn(true);
        assertTrue(ComCached.silentLogin());
    }

    @Test
    public void logout() {
        ComCached.logout();
    }

    @Test
    public void addAction() {
        when(Communication.addAction("a", 1, 1, 1, null)).thenReturn(true);
        assertTrue(ComCached.addAction("a", 1, 1, 1));
    }

    @Test
    public void addActionAdvanced() {
        when(Communication.addAction("a", 1, 1, 1, null)).thenReturn(true);
        assertTrue(ComCached.addAction("a", 1, 1, 1, null));
    }

    @Test
    public void getLastThreeActions() {
        ArrayList<Action> lastChanged = new ArrayList<>();
        lastChanged.add(new Action("a", "aa", 1));
        lastChanged.add(new Action("b", "bb", 2));
        when(Communication.getLastThreeActions()).thenReturn(lastChanged);
        assertEquals(lastChanged, ComCached.getLastThreeActions());
    }

    @Test
    public void getMyTotalScore() {
        ComCached.setTotalChanged(null);
        int total = 123;
        when(Communication.getMyTotalScore()).thenReturn(total);
        assertEquals(total, ComCached.getMyTotalScore());
    }

    @Test
    public void getUser() throws Exception {
        ComCached.setFriendsChanged(null);
        User user = new User("abc", "cba");
        when(Communication.class, "getUser").thenReturn(user);
        assertEquals(user, ComCached.getUser());
    }

    @Test
    public void addFriend() {
        when(Communication.addFriend("a")).thenReturn(true);
        when(Communication.addFriend("b")).thenReturn(false);
        assertTrue(ComCached.addFriend("a"));
        assertFalse(ComCached.addFriend("b"));
    }

    @Test
    public void getFriends() {
        ComCached.setFriendsChanged(null);
        ArrayList<CompareFriends> friendsChanged = new ArrayList<>();
        friendsChanged.add(new CompareFriends("a", 1));
        friendsChanged.add(new CompareFriends("b", 2));
        when(Communication.getFriends()).thenReturn(friendsChanged);
        assertEquals(friendsChanged, ComCached.getFriends());
    }

    @Test
    public void carbon() {
        ComCached.setCarbonChanged(null);
        Action carbonChanged = new Action(1, 1);
        when(Communication.carbon()).thenReturn(carbonChanged);
        assertEquals(carbonChanged, ComCached.carbon());
    }

    @Test
    public void getRecentCOSavings() {
        ComCached.setCoChanged(null);
        double[] coChanged = new double[]{1, 2, 3};
        when(Communication.getRecentCOSavings()).thenReturn(coChanged);
        assertEquals(coChanged, ComCached.getRecentCOSavings());
    }

    @Test
    public void setUserChanged() {
        User user = new User("abc", "cba");
        ComCached.setUserChanged(user);
        assertEquals(user, ComCached.getUser());
    }

    @Test
    public void setTotalChanged() {
        int total = 123;
        ComCached.setTotalChanged(total);
        assertEquals(total, ComCached.getMyTotalScore());
    }

    @Test
    public void setLastChanged() {
        ArrayList<Action> lastChanged = new ArrayList<>();
        lastChanged.add(new Action("a", "aa", 1));
        lastChanged.add(new Action("b", "bb", 2));
        ComCached.setLastChanged(lastChanged);
        assertEquals(lastChanged, ComCached.getLastThreeActions());
    }

    @Test
    public void setCoChanged() {
        double[] coChanged = new double[]{1, 2, 3};
        ComCached.setCoChanged(coChanged);
        assertEquals(coChanged, ComCached.getRecentCOSavings());
    }

    @Test
    public void setCarbonChanged() {
        Action carbonChanged = new Action(1, 1);
        ComCached.setCarbonChanged(carbonChanged);
        assertEquals(carbonChanged, ComCached.carbon());
    }

    @Test
    public void setFriendsChanged() {
        ArrayList<CompareFriends> friendsChanged = new ArrayList<>();
        friendsChanged.add(new CompareFriends("a", 1));
        friendsChanged.add(new CompareFriends("b", 2));
        ComCached.setFriendsChanged(friendsChanged);
        assertEquals(friendsChanged, ComCached.getFriends());
    }
}