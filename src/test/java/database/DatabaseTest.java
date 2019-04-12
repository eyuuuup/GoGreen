package database;

import org.joda.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import server.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith (PowerMockRunner.class)
@PrepareForTest ({DriverManager.class, Connection.class})
public class DatabaseTest {
    private Connection        connection;
    private PreparedStatement state;
    private ResultSet         resultSet;
    
    @Before
    public void setUp() throws Exception {
        resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        
        state = mock(PreparedStatement.class);
        when(state, "executeQuery").thenReturn(resultSet);
        
        connection = mock(Connection.class);
        when(connection, "prepareStatement", anyObject()).thenReturn(state);
        
        mockStatic(DriverManager.class);
        when(DriverManager.class, "getConnection").thenReturn(connection);

        Whitebox.setInternalState(Database.class, "connection", connection);
        Database.prepare();
    }

    @Test
    public void connect() throws SQLException {
        Database.connect();
        verifyStatic();
        DriverManager.getConnection();
    }

    @Test
    public void connectException() throws SQLException {
        PowerMockito.mockStatic(DriverManager.class);
        PowerMockito.when(DriverManager.getConnection()).thenThrow(new SQLException("test"));
        Database.connect();
    }

    @Test
    public void prepare() {
        Database.prepare();
    }

    @Test
    public void prepareException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("test"));
        Database.prepare();
    }
    
    @Test
    public void disconnect() throws SQLException {
        Database.disconnect();
    }
    
    @Test
    public void disconnectException() throws SQLException {
        Mockito.doThrow(new SQLException("test")).when(connection).close();
        Database.disconnect();
    }
    
    @Test
    public void defaultConstructor() {
        Database database = new Database();
        assertNotNull(database);
    }
    
    @Test
    public void getUsername() throws Exception {
        when(resultSet.getString(1)).thenReturn("jan");
        assertEquals("jan", Database.getUsername("token"));
        assertNotEquals("Jan", Database.getUsername("token"));
    }
    
    @Test
    public void getUsernameError() throws Exception {
        when(resultSet.getString(1)).thenThrow(SQLException.class);
        assertEquals("no username found", Database.getUsername("token"));
    }
    
    @Test
    public void getUser() throws Exception {
        when(resultSet.getString(1)).thenReturn("username");
        when(resultSet.getString(2)).thenReturn("email");
        when(resultSet.getInt(3)).thenReturn(100);
        
        User user = Database.getUser("token");
        
        assertEquals("username", user.getName());
        assertEquals("email", user.getEmail());
        assertEquals(100, user.getTotalScore());
    }
    
    @Test
    public void getUserInvalid() throws Exception {
        when(resultSet.next()).thenReturn(false);
        
        assertNull(Database.getUser("testUser"));
    }
    
    @Test
    public void getUserException() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        assertNull(Database.getUser("testToken"));
    }
    
    @Test
    public void addActionError() throws Exception {
        Action action = Mockito.mock(Action.class);
        when(resultSet.getInt(1)).thenThrow(SQLException.class);
        
        assertFalse(Database.addAction(action));
    }
    
    @Test
    public void addActionNonFood() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        
        Action action = Mockito.mock(Action.class);
        when(resultSet.getInt(1)).thenReturn(10);
        when(resultSet.getInt(2)).thenReturn(100);
        
        when(resultSet.getDouble(1)).thenReturn(new Double(200));
        when(resultSet.getDouble(2)).thenReturn(new Double(300));
        
        assertTrue(Database.addAction(action));
    }
    
    @Test
    public void addActionFood() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        
        Action action = Mockito.mock(Action.class);
        when(resultSet.getInt(1)).thenReturn(10);
        when(resultSet.getInt(2)).thenReturn(1);
        
        when(resultSet.getLong(1)).thenReturn(Instant.now().getMillis());
        
        when(resultSet.getDouble(1)).thenReturn(new Double(200));
        when(resultSet.getDouble(2)).thenReturn(new Double(300));
        
        assertTrue(Database.addAction(action));
    }
    
    @Test
    public void addActionFoodWrongTime() throws SQLException {
        Action action = Mockito.mock(Action.class);
        when(resultSet.getInt(1)).thenReturn(10);
        when(resultSet.getInt(2)).thenReturn(1);
        
        when(resultSet.getLong(1)).thenReturn(Instant.now().getMillis() - 2000);
        
        assertFalse(Database.addAction(action));
    }
    
    @Test
    public void retract() throws Exception {
        when(resultSet.getString(1)).thenReturn("action");
        when(resultSet.getInt(2)).thenReturn(100);
        when(resultSet.getInt(3)).thenReturn(200);
        when(resultSet.getInt(4)).thenReturn(300);
        when(resultSet.getLong(5)).thenReturn(new Long(10000));
        
        ActionList actionList = Database.retract("token");
        
        Action action = actionList.getList().get(0);
        
        assertEquals("action", action.getAction());
        assertEquals(100, action.getValue());
        assertEquals(200, action.getCarbonProduced(), 0);
        assertEquals(300, action.getCarbonReduced(), 0);
        assertEquals(10000, action.getDate());
    }
    
    @Test
    public void retractError() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        
        assertNull(Database.retract("token"));
    }
    
    @Test
    public void getCarbonValues() throws Exception {
        when(resultSet.getDouble(1)).thenReturn(new Double(1));
        when(resultSet.getDouble(2)).thenReturn(new Double(2));
        
        Action action = Database.getCarbonValues("token");
        
        assertEquals(1, action.getCarbonProduced(), 0);
        assertEquals(2, action.getCarbonReduced(), 0);
    }
    
    @Test
    public void getCarbonValuesNull() throws Exception {
        when(resultSet.next()).thenReturn(false);
        assertNull(Database.getCarbonValues("token"));
    }
    
    @Test
    public void getCarbonValuesException() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        assertNull(Database.getCarbonValues("token"));
    }
    
    @Test
    public void updateTotalScores() {
        Database.updateTotalScores("token", 10, 50, 50);
    }
    
    @Test
    public void updateTotalScoresError() throws Exception {
        when(state, "executeUpdate").thenThrow(SQLException.class);
        Database.updateTotalScores("token", 10, 50, 50);
    }
    
    @Test
    public void getCarbonReduced() throws Exception {
        when(resultSet.getDouble(1)).thenReturn(new Double(2));
        
        assertEquals(2, Database.getCarbonReduced("token"), 0);
    }
    
    @Test
    public void getCarbonReducedException() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        
        assertEquals(0, Database.getCarbonReduced("token"), 0);
    }
    
    @Test
    public void getCarbonProduced() throws Exception {
        when(resultSet.getDouble(1)).thenReturn(new Double(2));
        
        assertEquals(2, Database.getCarbonProduced("token"), 0);
    }
    
    @Test
    public void getCarbonProducedException() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        
        assertEquals(0, Database.getCarbonProduced("token"), 0);
    }
    
    @Test
    public void getTotalScore() throws Exception {
        when(resultSet.getInt(1)).thenReturn(400);
        
        assertEquals(400, Database.getTotalScore("token"));
    }
    
    @Test
    public void getTotalScoreError() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        when(resultSet.getInt(1)).thenReturn(400);
        
        assertEquals(0, Database.getTotalScore("username"));
    }
    
    @Test
    public void getTotalScoreByUser() throws Exception {
        when(resultSet.getInt(1)).thenReturn(100);
        
        assertEquals(100, Database.getTotalScoreByUser("username"));
    }
    
    @Test
    public void getTotalScoreByUserError() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        when(resultSet.getInt(1)).thenReturn(100);
        
        assertEquals(0, Database.getTotalScoreByUser("username"));
    }
    
    @Test
    public void silentLoginCheck() {
        assertTrue(Database.silentLoginCheck("token"));
    }
    
    @Test
    public void silentLoginCheckNoToken() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        assertFalse(Database.silentLoginCheck("token"));
    }
    
    @Test
    public void silentLoginCheckError() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        assertFalse(Database.silentLoginCheck("token"));
    }
    
    @Test
    public void register() {
        PreparedStatement state = Mockito.mock(PreparedStatement.class);
        User              user  = Mockito.mock(User.class);
        Database.register(user, "token");
    }
    
    @Test
    public void registerException() throws Exception {
        when(state, "executeUpdate").thenThrow(SQLException.class);
        User user = Mockito.mock(User.class);
        Database.register(user, "token");
    }
    
    @Test
    public void checkUsername() throws SQLException {
        when(resultSet.getString(1)).thenReturn("jan");
        
        assertTrue(Database.checkUsername("jan"));
    }
    
    @Test
    public void checkUsernameWrong() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        
        assertFalse(Database.checkUsername("Username"));
    }
    
    @Test
    public void checkUsernameError() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        
        assertFalse(Database.checkUsername("jan"));
    }
    
    @Test
    public void checkLoginCheckUsernameFalse() throws SQLException {
        User user = Mockito.mock(User.class);
        when(resultSet.next()).thenReturn(false);
        
        TokenResponse response = Database.checkLogin(user);
        assertFalse(response.isLegit());
        assertEquals(null, response.getToken());
    }
    
    @Test
    public void checkLoginTrue() throws Exception {
        User user = mock(User.class);
        when(user, "getName").thenReturn("name");
        
        when(resultSet.getString(1)).thenReturn("token");
        
        TokenResponse response = Database.checkLogin(user);
        assertTrue(response.isLegit());
        assertEquals("token", response.getToken());
    }
    
    @Test
    public void checkLoginError() throws Exception {
        User user = mock(User.class);
        when(user, "getName").thenReturn("name");
        
        when(resultSet.getString(1)).thenThrow(SQLException.class);
        
        TokenResponse response = Database.checkLogin(user);
        assertFalse(response.isLegit());
        assertEquals(null, response.getToken());
    }
    
    @Test
    public void addFriend() {
        CompareFriends friend = Mockito.mock(CompareFriends.class);
        assertTrue(Database.addFriend(friend));
    }
    
    @Test
    public void addFriendError() throws Exception {
        when(resultSet.getString(1)).thenReturn("username");
        when(state, "setString", 1, "username").thenThrow(SQLException.class);
        
        CompareFriends friend = Mockito.mock(CompareFriends.class);
        assertFalse(Database.addFriend(friend));
    }
    
    @Test
    public void showFriends() throws Exception {
        when(resultSet.getString(1)).thenReturn("username");
        
        FriendsList               friends     = Database.showFriends("token");
        ArrayList<CompareFriends> friendsList = friends.getList();
        CompareFriends            friend      = friendsList.get(0);
        assertEquals(0, friend.getScore());
        assertEquals(friend.getUsername(), "username");
    }
    
    @Test
    public void showFriendsError() throws Exception {
        when(state, "setString", 1, "username").thenThrow(SQLException.class);
        when(resultSet.getString(1)).thenReturn("username");
        assertNull(Database.showFriends("token"));
    }
    
    @Test
    public void showFollowers() throws SQLException {
        when(resultSet.getString(1)).thenReturn("username");
        when(resultSet.getInt(2)).thenReturn(100);
        
        
        FriendsList               friends     = Database.showFollowers("token");
        ArrayList<CompareFriends> friendsList = friends.getList();
        CompareFriends            friend      = friendsList.get(0);
        assertEquals(100, friend.getScore());
        assertEquals(friend.getUsername(), "username");
    }
    
    @Test
    public void showFollowersError() throws Exception {
        when(state, "setString", 1, "username").thenThrow(SQLException.class);
        when(resultSet.getString(1)).thenReturn("username");
        assertNull(Database.showFollowers("token"));
    }
    
    @Test
    public void getLeaderboard() throws SQLException {
        when(resultSet.getString(1)).thenReturn("username");
        when(resultSet.getInt(2)).thenReturn(100);
        
        
        FriendsList               friends     = Database.getLeaderboard();
        ArrayList<CompareFriends> friendsList = friends.getList();
        CompareFriends            friend      = friendsList.get(0);
        assertEquals(100, friend.getScore());
        assertEquals(friend.getUsername(), "username");
    }
    
    @Test
    public void getLeaderboardError() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        assertNull(Database.getLeaderboard());
    }
    
    @Test
    public void getLastMeal() throws Exception {
        when(resultSet.getLong(1)).thenReturn(new Long(100));
        assertEquals(100, Database.getLastMeal("testToken"));
    }
    
    @Test
    public void getLastMealException() throws Exception {
        when(state, "executeQuery").thenThrow(SQLException.class);
        assertEquals(0, Database.getLastMeal("testToken"));
    }

    @Test
    public void addChallenge() {
        assertTrue(Database.addChallenge("one", "two", 100));
    }

    @Test
    public void addChallengeException() throws Exception {
        when(state, "executeUpdate").thenThrow(SQLException.class);
        assertFalse(Database.addChallenge("one", "two", 100));
    }

    @Test
    public void retrieveChallenges() throws Exception{
        when(resultSet.getString(1)).thenReturn("StringOne");
        when(resultSet.getString(2)).thenReturn("StringTwo");
        when(resultSet.getInt(3)).thenReturn(100);
        when(resultSet.getInt(4)).thenReturn(200);
        when(resultSet.getInt(5)).thenReturn(300);
        when(resultSet.getInt(6)).thenReturn(400);

        ArrayList challenges = Database.retrieveChallenges("token");
        Challenge challenge = (Challenge) challenges.get(0);

        assertEquals("StringOne", challenge.getUserA());
        assertEquals("StringTwo", challenge.getUserB());
        assertEquals(100, challenge.getScoreA());
        assertEquals(200, challenge.getScoreB());
        assertEquals(300, challenge.getGoal());
        assertEquals(400, challenge.getState());
        assertTrue(challenge.isOnA());
    }

    @Test
    public void retrieveChallengesNotOnA() throws Exception{
        when(resultSet.getString(1)).thenReturn("NotStringOne").thenReturn("StringOne");
        when(resultSet.getString(2)).thenReturn("StringTwo");
        when(resultSet.getInt(3)).thenReturn(100);
        when(resultSet.getInt(4)).thenReturn(200);
        when(resultSet.getInt(5)).thenReturn(300);
        when(resultSet.getInt(6)).thenReturn(400);

        ArrayList challenges = Database.retrieveChallenges("token");
        Challenge challenge = (Challenge) challenges.get(0);

        assertEquals("StringOne", challenge.getUserA());
        assertEquals("StringTwo", challenge.getUserB());
        assertEquals(100, challenge.getScoreA());
        assertEquals(200, challenge.getScoreB());
        assertEquals(300, challenge.getGoal());
        assertEquals(400, challenge.getState());
        assertFalse(challenge.isOnA());
    }

    @Test
    public void retrieveChallengesException() throws Exception{
        when(state, "executeQuery").thenThrow(SQLException.class);
        assertNull(Database.retrieveChallenges("token"));
    }
    
    @Test
    public void getRecentCOSavings() throws SQLException {
        when(resultSet.getDouble(1)).thenReturn(10.0);
        
        Action a = new Action();
        a.setCarbonReduced(10.0);
        
        ArrayList<Action> result = Database.getRecentCOSavings("token");
        
        assertEquals(1, result.size());
        assertEquals(a.getCarbonReduced(), result.get(0).getCarbonReduced(), 0);
    }

    @Test
    public void initializeChallenge() throws Exception {
        when(state, "executeUpdate").thenReturn(1);
        assertTrue(Database.initializeChallenge("token", "user"));
    }

    @Test
    public void initializeChallengeFalse() throws Exception {
        when(state, "executeUpdate").thenReturn(10);
        assertFalse(Database.initializeChallenge("token", "user"));
    }

    @Test
    public void initializeChallengeException() throws Exception {
        when(state, "executeUpdate").thenThrow(new SQLException("test"));
        assertFalse(Database.initializeChallenge("token", "user"));
    }

    @Test
    public void evaluateChallenges() throws Exception {
        Whitebox.invokeMethod(Database.class, "evaluateChallenges", "token");
    }

    @Test
    public void evaluateChallengesException() throws Exception {
        when(state, "executeQuery").thenThrow(new SQLException("test"));
        Whitebox.invokeMethod(Database.class, "evaluateChallenges", "token");
    }

    @Test
    public void evaluateChallenge() throws Exception {
        when(resultSet.getInt(1)).thenReturn(10);
        when(resultSet.getInt(2)).thenReturn(20);
        Whitebox.invokeMethod(Database.class, "evaluateChallenge", "user", 100, state, 2);
    }

    @Test
    public void evaluateChallengeScoreSmaller() throws Exception {
        when(resultSet.getInt(1)).thenReturn(10);
        when(resultSet.getInt(2)).thenReturn(20);
        Whitebox.invokeMethod(Database.class, "evaluateChallenge", "user", 29, state, 2);
    }

    @Test
    public void updateChallenge() throws Exception {
        when(state, "executeUpdate").thenReturn(1);
        assertTrue(Database.updateChallenge(new Long(100), "A", "B", 1));
    }

    @Test
    public void updateChallengeFalse() throws Exception {
        when(state, "executeUpdate").thenReturn(2);
        assertFalse(Database.updateChallenge(new Long(100), "A", "B", 1));
    }

    @Test
    public void updateChallengeException() throws Exception {
        when(state, "executeUpdate").thenThrow(new SQLException("test"));
        assertFalse(Database.updateChallenge(new Long(100), "A", "B", 1));
    }

    @Test
    public void getOTE() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(12).thenReturn(13).thenReturn(14);

        OnLoadValues  ote = Database.getOTE("token");

        assertTrue(ote.isElectricCar());
        assertTrue(ote.isEnvGroup());
        assertTrue(ote.isSolarPanel());
    }

    @Test
    public void getOTEnoSolarPanels() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(13).thenReturn(14);

        OnLoadValues  ote = Database.getOTE("token");

        assertTrue(ote.isElectricCar());
        assertTrue(ote.isEnvGroup());
        assertFalse(ote.isSolarPanel());
    }

    @Test
    public void getOTENothing() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(1);

        OnLoadValues  ote = Database.getOTE("token");

        assertFalse(ote.isElectricCar());
        assertFalse(ote.isEnvGroup());
        assertFalse(ote.isSolarPanel());
    }

    @Test
    public void getOTEException() throws Exception {
        when(state, "executeQuery").thenThrow(new SQLException("test"));

        OnLoadValues  ote = Database.getOTE("token");

        assertFalse(ote.isElectricCar());
        assertFalse(ote.isEnvGroup());
        assertFalse(ote.isSolarPanel());
    }
    
    @Test
    public void getRecentCOSavingsException() throws SQLException {
        when(resultSet.getDouble(1)).thenThrow(new SQLException("test"));
        
        assertNull(Database.getRecentCOSavings("token"));
    }
}