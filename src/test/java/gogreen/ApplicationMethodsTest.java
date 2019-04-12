package gogreen;

import client.Communication;
import client.OnLoadValues;
import client.User;
import com.google.common.hash.Hashing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith (PowerMockRunner.class)
@PrepareForTest ({Communication.class, Application.class, Transport.class, Energy.class})
public class ApplicationMethodsTest {
    private String encodedUsername;
    private String hashedPassword;
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
        PowerMockito.mockStatic(Application.class);
        PowerMockito.mockStatic(Energy.class);
        PowerMockito.mockStatic(Transport.class);
        
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
    public void encodeUsernameManual() {
        String decoded = "testUsername123";
        String encoded = "dGVzdFVzZXJuYW1lMTIz";
        assertEquals(encoded, ApplicationMethods.encodeUsername(decoded));
    }
    
    @Test
    public void decodeUsernameManual() {
        String decoded = "testUsername123";
        String encoded = "dGVzdFVzZXJuYW1lMTIz";
        assertEquals(decoded, ApplicationMethods.decodeUsername(encoded));
    }
    
    @Test (expected = NullPointerException.class)
    public void checkNameNull() throws Exception {
        Whitebox.invokeMethod(ApplicationMethods.class, "checkName", (String) null);
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

    @Test
    public void setPresets() {
        OnLoadValues onLoadValues = Mockito.mock(OnLoadValues.class);
        User user = Mockito.mock(User.class);
        when(Communication.onLoad()).thenReturn(onLoadValues);
        Mockito.when(onLoadValues.getUser()).thenReturn(user);

        ApplicationMethods.setPresets();

        verifyStatic();
        Transport.setHasElectricCar(false);
        verifyStatic();
        Energy.setHasSolarPanels(false);
    }

    @Test
    public void setPresetsTwoOTE() {
        OnLoadValues onLoadValues = Mockito.mock(OnLoadValues.class);
        User user = Mockito.mock(User.class);
        when(Communication.onLoad()).thenReturn(onLoadValues);
        Mockito.when(onLoadValues.getUser()).thenReturn(user);
        Mockito.when(onLoadValues.isElectricCar()).thenReturn(true);
        Mockito.when(onLoadValues.isSolarPanel()).thenReturn(true);

        ApplicationMethods.setPresets();

        verifyStatic();
        Transport.setHasElectricCar(true);
        verifyStatic();
        Energy.setHasSolarPanels(true);
    }

    @Test
    public void setPresetsException() throws Exception {
        when(Communication.class, "onLoad").thenReturn(null);
        ApplicationMethods.setPresets();
    }

    @Test
    public void getChallengeProgressNegative() {
        Whitebox.setInternalState(ApplicationMethods.class, "points", 0);
        assertEquals(0, ApplicationMethods.getChallengeProgress(10, 10), 0);
    }

    @Test
    public void getChallengeProgressPositive() {
        Whitebox.setInternalState(ApplicationMethods.class, "points", 100);
        assertEquals(9, ApplicationMethods.getChallengeProgress(10, 10), 0);
    }

    @Test
    public void getChallengeProgressExactlyZero() {
        Whitebox.setInternalState(ApplicationMethods.class, "points", 0);
        assertEquals(0, ApplicationMethods.getChallengeProgress(0, 10), 0);
    }

    @Test (expected = ArithmeticException.class)
    public void getChallengeProgressDivideByZero() {
        Whitebox.setInternalState(ApplicationMethods.class, "points", 20);
        assertEquals(0, ApplicationMethods.getChallengeProgress(10, 0), 0);
    }

    @Test
    public void getPoints() {
        Whitebox.setInternalState(ApplicationMethods.class, "points", 20);
        assertEquals(20, ApplicationMethods.getPoints());
        assertNotEquals(30, ApplicationMethods.getPoints());
    }

    @Test
    public void getFollowingSize() {
        Whitebox.setInternalState(ApplicationMethods.class, "followingSize", 5);
        assertEquals(5, ApplicationMethods.getFollowingSize());
        assertNotEquals(0, ApplicationMethods.getFollowingSize());
    }

    @Test
    public void getFollowersSize() {
        Whitebox.setInternalState(ApplicationMethods.class, "followersSize", 8);
        assertEquals(8, ApplicationMethods.getFollowersSize());
        assertNotEquals(200, ApplicationMethods.getFollowersSize());
    }

    @Test
    public void getUsername() {
        Whitebox.setInternalState(ApplicationMethods.class, "username", "Username");
        assertEquals("Username", ApplicationMethods.getUsername());
        assertNotEquals("name", ApplicationMethods.getUsername());
    }

    @Test
    public void getSavedCarbon() {
        Whitebox.setInternalState(ApplicationMethods.class, "savedCarbon", 200.3);
        assertEquals(200.3, ApplicationMethods.getSavedCarbon(), 0);
        assertNotEquals(180.4, ApplicationMethods.getSavedCarbon(), 0);
    }

    @Test
    public void isSolarPanel() {
        Whitebox.setInternalState(ApplicationMethods.class, "solarPanel", true);
        assertTrue(ApplicationMethods.isSolarPanel());
    }

    @Test
    public void isElectricCar() {
        Whitebox.setInternalState(ApplicationMethods.class, "electricCar", false);
        assertFalse(ApplicationMethods.isElectricCar());
    }

    @Test
    public void isEnvGroup() {
        Whitebox.setInternalState(ApplicationMethods.class, "envGroup", true);
        assertTrue(ApplicationMethods.isEnvGroup());
    }
    
    @Test
    public void decodeUsername() {
        String name = ApplicationMethods.encodeUsername("username");
        assertEquals("username", ApplicationMethods.decodeUsername(name));
    }
    
    @Test
    public void decodeUsernameWrong() {
        String name = ApplicationMethods.encodeUsername("username");
        assertNotEquals("user", ApplicationMethods.decodeUsername(name));
    }
    
    @Test
    public void onLoad() {
        ApplicationMethods.onLoad();
    }
}