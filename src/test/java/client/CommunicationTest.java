package client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

@RunWith (PowerMockRunner.class)
@PrepareForTest (Communication.class)
public class CommunicationTest {
    
    // ========== PART METHODS =================================================
    
    @Test
    public void submitFalse() throws Exception {
        RestTemplate  template = PowerMockito.mock(RestTemplate.class);
        TokenResponse response = new TokenResponse("test", false);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(TokenResponse.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertFalse(Whitebox.invokeMethod(Communication.class, "submit", new Object[]{"user", "pwd", true, "/url"}));
    }
    
    @Test
    public void submitTrue() throws Exception {
        RestTemplate  template = PowerMockito.mock(RestTemplate.class);
        TokenResponse response = new TokenResponse("test", true);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(TokenResponse.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertTrue(Whitebox.invokeMethod(Communication.class, "submit", new Object[]{"user", "pwd", true, "/url"}));
    }
    
    @Test
    public void submitWrongFile() throws Exception {
        RestTemplate  template = PowerMockito.mock(RestTemplate.class);
        TokenResponse response = new TokenResponse("test", true);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(TokenResponse.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        PowerMockito.whenNew(FileWriter.class).withArguments(Matchers.anyString()).thenThrow(new FileNotFoundException());
        
        assertTrue(Whitebox.invokeMethod(Communication.class, "submit", new Object[]{"user", "pwd", true, "/url"}));
    }
    
    @Test
    public void postToken() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        Object       object   = new Integer(42);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(Object.class))).thenReturn(object);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertEquals(object, Whitebox.invokeMethod(Communication.class, "postToken", new Object[]{"/url", Object.class}));
    }
    
    // ========== USER AUTHENTICATION ==========================================
    
    @Test
    public void register() throws Exception {
        RestTemplate  template = PowerMockito.mock(RestTemplate.class);
        TokenResponse response = new TokenResponse("test", true);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(TokenResponse.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertTrue(Communication.register("user", "pwd", true));
    }
    
    @Test
    public void login() throws Exception {
        RestTemplate  template = PowerMockito.mock(RestTemplate.class);
        TokenResponse response = new TokenResponse("test", true);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(TokenResponse.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertTrue(Communication.login("user", "pwd", true));
    }

//    @Test
//    public void silentLogin() throws Exception {
//        BufferedReader reader = PowerMockito.mock(BufferedReader.class);
//        PowerMockito.when(reader.readLine()).thenReturn("testLineFromFile");
//        PowerMockito.whenNew(BufferedReader.class).withAnyArguments().thenReturn(reader);
//
//
//        RestTemplate template = PowerMockito.mock(RestTemplate.class);
//        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(boolean.class))).thenReturn(true);
//        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
//
//        assertTrue(Communication.silentLogin());
//    }
    
    @Test
    public void silentLoginWrongFile() throws Exception {
        PowerMockito.whenNew(FileReader.class).withArguments(Matchers.anyString()).thenThrow(new FileNotFoundException());
        
        assertFalse(Communication.silentLogin());
    }
    
    @Test
    public void logout() {
        Communication.logout();
    }
    
    @Test
    public void isLoggedIn() throws Exception {
        Whitebox.invokeMethod(Communication.class, "isLoggedIn");
    }
    
    // ========== ACTION HANDLERS ==============================================
    
    @Test
    public void addAction() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(boolean.class))).thenReturn(true);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertTrue(Communication.addAction("testAction", 100, 50, 50));
    }
    
    @Test
    public void getLastThreeActions() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        ArrayList<Action> list = new ArrayList<>();
        ActionList   response = new ActionList(list);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(ActionList.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        ArrayList answer = Communication.getLastThreeActions();
        
        assertEquals(list, answer);
    }
    
    @Test
    public void getMyTotalScore() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(Integer.class))).thenReturn(150);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertEquals(150, Communication.getMyTotalScore());
    }
    
    // ========== SOCIAL HANDLERS ==============================================
    
    @Test
    public void checkUsername() throws Exception {
        String       request  = "testUser";
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        boolean      response = true;
        PowerMockito.when(template.postForObject(anyString(), anyString(), eq(boolean.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        boolean answer = Communication.checkUsername(request);
        
        assertEquals(response, answer);
    }
    
    @Test
    public void addFriend() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(boolean.class))).thenReturn(true);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        assertTrue(Communication.addFriend("addFriend"));
    }
    
    @Test
    public void getFriends() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        FriendsList  response = new FriendsList();
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(FriendsList.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        ArrayList answer = Communication.getFriends();
        
        assertEquals(response.getList(), answer);
    }
    
    @Test
    public void getFollowers() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        FriendsList  response = new FriendsList();
        PowerMockito.when(template.postForObject(anyString(), anyObject(), eq(FriendsList.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        ArrayList answer = Communication.getFollowers();
        
        assertEquals(response.getList(), answer);
    }
    
    @Test
    public void getLeaderboard() throws Exception {
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        FriendsList  response = new FriendsList();
        PowerMockito.when(template.getForObject(anyString(), eq(FriendsList.class))).thenReturn(response);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);
        
        ArrayList answer = Communication.getLeaderboard();
        
        assertEquals(response.getList(), answer);
    }
}