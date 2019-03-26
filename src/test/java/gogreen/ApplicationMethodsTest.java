package gogreen;

import client.Communication;
import com.google.common.hash.Hashing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith (PowerMockRunner.class)
@PrepareForTest ({Communication.class, Application.class})
public class ApplicationMethodsTest {
    private String encodedUsername;
    private String hashedPassword;
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
        PowerMockito.mockStatic(Application.class);
        
        String username = "username";
        encodedUsername = Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
        
        String password = "password";
        hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
    
    @Test
    public void login() throws IllegalAccessException {
        ArgumentCaptor<String>  passwordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String>  usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> rememberCaptor = ArgumentCaptor.forClass(Boolean.class);
        
        when(Communication.login(anyString(), anyString(), anyBoolean())).thenReturn(true);
        
        ApplicationMethods.login("username", "password", true);
        PowerMockito.verifyStatic();
        Communication.login(usernameCaptor.capture(), passwordCaptor.capture(), rememberCaptor.capture());
        assertEquals(encodedUsername, usernameCaptor.getValue());
        assertEquals(hashedPassword, passwordCaptor.getValue());
        assertTrue(rememberCaptor.getValue());
        
        PowerMockito.verifyStatic();
        Application.mainScreen();
    }
    
    @Test (expected = IllegalAccessException.class)
    public void loginNotInDatabase() throws IllegalAccessException {
        when(Communication.login(anyString(), anyString(), anyBoolean())).thenReturn(false);
        ApplicationMethods.login("username", "password", true);
    }
    
    @Test
    public void register() throws IllegalAccessException, FileNotFoundException {
        ArgumentCaptor<String>  passwordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String>  usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> rememberCaptor = ArgumentCaptor.forClass(Boolean.class);
        
        when(Communication.register(anyString(), anyString(), anyBoolean())).thenReturn(true);
        
        ApplicationMethods.register("username", "password", "password", true);
        PowerMockito.verifyStatic();
        Communication.register(usernameCaptor.capture(), passwordCaptor.capture(), rememberCaptor.capture());
        assertEquals(encodedUsername, usernameCaptor.getValue());
        assertEquals(hashedPassword, passwordCaptor.getValue());
        assertTrue(rememberCaptor.getValue());
        
        PowerMockito.verifyNoMoreInteractions();
    }
    
    @Test (expected = IllegalAccessException.class)
    public void registrationNotAccepted() throws IllegalAccessException, FileNotFoundException {
        when(Communication.register(anyString(), anyString(), anyBoolean())).thenReturn(false);
        ApplicationMethods.register("username", "password", "password", true);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void registrationPasswordsNotEqual() throws IllegalAccessException, FileNotFoundException {
        ApplicationMethods.register("username", "password", "passwordTwo", true);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void registrationPasswordsTooShort() throws IllegalAccessException, FileNotFoundException {
        ApplicationMethods.register("username", "p", "p", true);
    }
    
    @Test
    public void encodeUsername() {
        String decoded = "testUsername123";
        String encoded = "dGVzdFVzZXJuYW1lMTIz";
        assertEquals(encoded, ApplicationMethods.encodeUsername(decoded));
    }
    
    @Test
    public void decodeUsername() {
        String decoded = "testUsername123";
        String encoded = "dGVzdFVzZXJuYW1lMTIz";
        assertEquals(decoded, ApplicationMethods.decodeUsername(encoded));
    }
    
    @Test (expected = NullPointerException.class)
    public void checkNameNull() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void checkNameTooLong() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void checkNameTooShort() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", "");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void checkNameIllegalCharacter() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", "2");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void checkNameIllegalCharacterTwo() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", "Username_");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void checkNameOffensiveName() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", "slut");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void checkNameOffensiveNameTwo() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", "SLUT");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void checkNameOffensiveNameThree() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", "usernameSLUT");
    }
    
    @Test
    public void hashPasswordLegitManual() throws Exception {
        String response = Whitebox.invokeMethod(ApplicationMethods.class, "hashPassword", "password");
        assertEquals(response.toUpperCase(), "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8");
    }
    
    @Test
    public void hashPasswordLegitCalculation() throws Exception {
        String response = Whitebox.invokeMethod(ApplicationMethods.class, "hashPassword", "password");
        assertEquals(response, Hashing.sha256().hashString("password", StandardCharsets.UTF_8).toString());
    }
    
    @Test
    public void getLevel() {
        assertEquals(1, ApplicationMethods.getLevel(0));
        assertEquals(1, ApplicationMethods.getLevel(249));
        assertEquals(2, ApplicationMethods.getLevel(250));
        assertEquals(2, ApplicationMethods.getLevel(549));
        assertEquals(3, ApplicationMethods.getLevel(550));
        assertEquals(3, ApplicationMethods.getLevel(899));
        assertEquals(4, ApplicationMethods.getLevel(900));
        assertEquals(4, ApplicationMethods.getLevel(1299));
        assertEquals(5, ApplicationMethods.getLevel(1300));
    }
    
    @Test
    public void getLevelInverse() {
        assertEquals(0, ApplicationMethods.getLevelInv(1));
        assertEquals(250, ApplicationMethods.getLevelInv(2));
        assertEquals(550, ApplicationMethods.getLevelInv(3));
        assertEquals(900, ApplicationMethods.getLevelInv(4));
        assertEquals(1300, ApplicationMethods.getLevelInv(5));
    }
    
    @Test
    public void getLevelProgress() {
        assertEquals(0.5, ApplicationMethods.getLevelProgress(125), 0.01);
        assertEquals(1, ApplicationMethods.getLevelProgress(249), 0.01);
        assertEquals(0, ApplicationMethods.getLevelProgress(250), 0.01);
    }
}