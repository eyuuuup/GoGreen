package database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import server.CompareFriends;
import server.FriendsList;
import server.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;

@RunWith (PowerMockRunner.class)
@PrepareForTest ({DriverManager.class, Connection.class})
public class DatabaseTest {
    private Connection        connection;
    private PreparedStatement state;
    private ResultSet         resultSet;
    
    @Before
    public void setUp() throws Exception {
        resultSet = PowerMockito.mock(ResultSet.class);
        PowerMockito.mockStatic(DriverManager.class);
        connection = PowerMockito.mock(Connection.class);
        state = PowerMockito.mock(PreparedStatement.class);
        PowerMockito.when(DriverManager.class, "getConnection").thenReturn(connection);
        PowerMockito.when(connection, "prepareStatement", anyObject()).thenReturn(state);
        PowerMockito.when(state, "executeQuery").thenReturn(resultSet);
        PowerMockito.when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
    }
    
    @Test
    public void defaultConstructor() {
        Database database = new Database();
    }
    
    @Test
    public void getUsername() throws Exception {
        PowerMockito.when(resultSet.getString(1)).thenReturn("jan");
        Database.getUsername("token");
    }
    
    @Test
    public void getUsernameError() throws Exception {
        PowerMockito.when(resultSet.getString(1)).thenThrow(SQLException.class);
        Database.getUsername("token");
    }
    
    @Test
    public void addAction() throws Exception {
        server.Action action = Mockito.mock(server.Action.class);
        
        int n = 1;
        PowerMockito.when(resultSet.getInt(1)).thenReturn(n);
        PowerMockito.when(resultSet.getInt(2)).thenReturn(n);
        
        Database.addAction(action);
    }
    
    @Test
    public void addActionTime() throws Exception {
        server.Action action = Mockito.mock(server.Action.class);
        
        int n = 1;
        PowerMockito.when(resultSet.getInt(1)).thenReturn(n);
        PowerMockito.when(resultSet.getInt(2)).thenReturn(n);
        
        Database.addAction(action);
    }
    
    @Test
    public void addActionError() throws Exception {
        server.Action action = Mockito.mock(server.Action.class);
        PowerMockito.when(resultSet.getInt(1)).thenThrow(SQLException.class);
        
        Database.addAction(action);
    }
    
    @Test
    public void retract() throws Exception {
        Long n = new Long(1);
        PowerMockito.when(resultSet.getString(1)).thenReturn("action");
        PowerMockito.when(resultSet.getLong(2)).thenReturn(n);
        
        Database.retract("token");
    }
    
    @Test
    public void retractError() throws Exception {
        PowerMockito.when(state, "executeQuery").thenThrow(SQLException.class);
        
        assertNull(Database.retract("token"));
    }
    
    @Test
    public void updateTotalScores() {
        Database.updateTotalScores("token", 10);
    }
    
    @Test
    public void updateTotalScoresError() throws Exception {
        PowerMockito.when(state, "executeUpdate").thenThrow(SQLException.class);
        Database.updateTotalScores("token", 10);
    }
    
    @Test
    public void getTotalScore() {
        Database.getTotalScore("token");
    }
    
    @Test
    public void getTotalScoreError() throws Exception {
        PowerMockito.when(state, "executeQuery").thenThrow(SQLException.class);
        
        Database.getTotalScore("username");
    }
    
    @Test
    public void getTotalScoreByUser() throws Exception {
        int n = 1;
        PowerMockito.when(resultSet.getInt(1)).thenReturn(n);
        
        Database.getTotalScoreByUser("username");
    }
    
    @Test
    public void getTotalScoreByUserError() throws Exception {
        PowerMockito.when(state, "executeQuery").thenThrow(SQLException.class);
        
        Database.getTotalScoreByUser("username");
    }
    
    @Test
    public void silentLoginCheck() {
        assertTrue(Database.silentLoginCheck("token"));
    }
    
    @Test
    public void silentLoginCheckNoToken() throws SQLException {
        PowerMockito.when(resultSet.next()).thenReturn(false);
        assertFalse(Database.silentLoginCheck("token"));
    }
    
    @Test
    public void silentLoginCheckError() throws Exception {
        PowerMockito.when(state, "setString", 1, "token").thenThrow(SQLException.class);
        assertFalse(Database.silentLoginCheck("token"));
    }
    
    @Test
    public void register() {
        server.User user = Mockito.mock(server.User.class);
        Database.register(user, "token");
    }
    
    @Test
    public void registerError() throws Exception {
        PowerMockito.when(state, "setString", 1, "token").thenThrow(SQLException.class);
        server.User user = Mockito.mock(server.User.class);
        Database.register(user, "token");
    }
    
    @Test
    public void checkUsername() throws SQLException {
        PowerMockito.when(resultSet.getString(2)).thenReturn("jan");
        
        assertTrue(Database.checkUsername("jan"));
    }
    
    @Test
    public void checkUsernameWrong() throws SQLException {
        PowerMockito.when(resultSet.getString(2)).thenReturn("jan");
        
        assertFalse(Database.checkUsername("Username"));
    }
    
    @Test
    public void checkUsernameError() throws SQLException {
        PowerMockito.when(resultSet.getString(2)).thenThrow(SQLException.class);
        
        assertFalse(Database.checkUsername("jan"));
    }
    
    @Test
    public void checkLoginCheckUsernameFalse() throws SQLException {
        User user = Mockito.mock(User.class);
        PowerMockito.when(resultSet.getString(2)).thenThrow(SQLException.class);
        
        server.TokenResponse response = Database.checkLogin(user);
        assertFalse(response.isLegit());
    }
    
    @Test
    public void checkLoginTrue() throws Exception {
        User user = PowerMockito.mock(User.class);
        PowerMockito.when(user, "getName").thenReturn("name");
        PowerMockito.when(resultSet.getString(2)).thenReturn("name");
        
        PowerMockito.when(resultSet.getString(1)).thenReturn("token");
        
        server.TokenResponse response = Database.checkLogin(user);
        assertTrue(response.isLegit());
    }
    
    @Test
    public void checkLoginError() throws Exception {
        User user = PowerMockito.mock(User.class);
        PowerMockito.when(user, "getName").thenReturn("name");
        PowerMockito.when(resultSet.getString(2)).thenReturn("name");
        
        PowerMockito.when(resultSet.getString(1)).thenThrow(SQLException.class);
        
        server.TokenResponse response = Database.checkLogin(user);
        assertFalse(response.isLegit());
    }
    
    @Test
    public void addFriend() {
        CompareFriends friend = Mockito.mock(CompareFriends.class);
        assertTrue(Database.addFriend(friend));
    }
    
    @Test
    public void addFriendError() throws Exception {
        PowerMockito.when(resultSet.getString(1)).thenReturn("username");
        PowerMockito.when(state, "setString", 1, "username").thenThrow(SQLException.class);
        
        CompareFriends friend = Mockito.mock(CompareFriends.class);
        assertFalse(Database.addFriend(friend));
    }
    
    @Test
    public void showFriends() throws Exception {
        PowerMockito.when(resultSet.getString(1)).thenReturn("username");
        
        FriendsList               friends     = Database.showFriends("token");
        ArrayList<CompareFriends> friendsList = friends.getList();
        CompareFriends            friend      = friendsList.get(0);
        assertEquals(0, friend.getScore());
        assertEquals(friend.getUsername(), "username");
    }
    
    @Test
    public void showFriendsError() throws Exception {
        PowerMockito.when(state, "setString", 1, "username").thenThrow(SQLException.class);
        PowerMockito.when(resultSet.getString(1)).thenReturn("username");
        assertNull(Database.showFriends("token"));
    }
    
    @Test
    public void showFollowers() throws SQLException {
        PowerMockito.when(resultSet.getString(1)).thenReturn("username");
        PowerMockito.when(resultSet.getInt(2)).thenReturn(100);
        
        
        FriendsList               friends     = Database.showFollowers("token");
        ArrayList<CompareFriends> friendsList = friends.getList();
        CompareFriends            friend      = friendsList.get(0);
        assertEquals(100, friend.getScore());
        assertEquals(friend.getUsername(), "username");
    }
    
    @Test
    public void showFollowersError() throws Exception {
        PowerMockito.when(state, "setString", 1, "username").thenThrow(SQLException.class);
        PowerMockito.when(resultSet.getString(1)).thenReturn("username");
        assertNull(Database.showFollowers("token"));
    }
    
    @Test
    public void getLeaderboard() throws SQLException {
        PowerMockito.when(resultSet.getString(1)).thenReturn("username");
        PowerMockito.when(resultSet.getInt(2)).thenReturn(100);
        
        
        FriendsList               friends     = Database.getLeaderboard();
        ArrayList<CompareFriends> friendsList = friends.getList();
        CompareFriends            friend      = friendsList.get(0);
        assertEquals(100, friend.getScore());
        assertEquals(friend.getUsername(), "username");
    }
    
    @Test
    public void getLeaderboardError() throws Exception {
        PowerMockito.when(state, "executeQuery").thenThrow(SQLException.class);
        assertNull(Database.getLeaderboard());
    }
    
    @Test
    public void getUser() throws SQLException {
        PowerMockito.when(resultSet.getString(1)).thenReturn("username");
        PowerMockito.when(resultSet.getString(2)).thenReturn("mail");
        PowerMockito.when(resultSet.getInt(3)).thenReturn(100);
        
        User user = Database.getUser("testToken");
        assertEquals(user.getName(), "username");
        assertEquals(user.getEmail(), "mail");
        assertEquals(100, user.getTotalScore());
    }
    
    @Test
    public void getUserNull() throws SQLException {
        PowerMockito.when(resultSet.next()).thenReturn(false);
        
        assertNull(Database.getUser("testToken"));
    }
    
    @Test
    public void getUserException() throws Exception {
        PowerMockito.when(state, "executeQuery").thenThrow(SQLException.class);
        assertNull(Database.getUser("testToken"));
    }
    
    @Test
    public void getLastMealException() throws Exception {
        PowerMockito.when(state, "executeQuery").thenThrow(SQLException.class);
        assertEquals(0, Database.getLastMeal("testToken"));
    }
    
    @Test
    public void addChallenge() throws Exception {
        PowerMockito.when(state, "executeUpdate").thenReturn(0);
        Database.addChallenge("a", "b", 1000);
    }
    
    @Test
    public void addChallengeException() throws Exception {
        PowerMockito.when(state, "executeUpdate").thenThrow(SQLException.class);
        Database.addChallenge("a", "b", 1000);
    }
}