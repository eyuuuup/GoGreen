package gogreen;

import client.Communication;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import javafx.scene.control.TextField;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.testfx.framework.junit.ApplicationTest;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Communication.class, Application.class})
public class ApplicationMethodsTest extends ApplicationTest {
    private String encodedUsername;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
        PowerMockito.mockStatic(Application.class);

        String username = "username";
        try {
            encodedUsername = Base64.getEncoder().encodeToString(
                    username.getBytes("utf-8"));
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {}

    @Test
    public void toggleVisibilityTrue() {
        TextField text = new TextField("test");
        PasswordField password = new PasswordField();
        ApplicationMethods.toggleVisibility(text, password, true);
        assertTrue(text.isVisible());
        assertFalse(password.isVisible());
    }

    @Test
    public void toggleVisibilityFalse() {
        TextField text = new TextField("test");
        PasswordField password = new PasswordField();
        ApplicationMethods.toggleVisibility(text, password, false);
        assertFalse(text.isVisible());
        assertTrue(password.isVisible());
    }

    @Test
    public void login() {
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> rememberCaptor = ArgumentCaptor.forClass(Boolean.class);

        StrongPasswordEncryptor passwordEncrypt = new StrongPasswordEncryptor();

        when(Communication.login(anyString(), anyString(), anyBoolean())).thenReturn(true);

        ApplicationMethods.login("username", "password", true);
        PowerMockito.verifyStatic();
        Communication.login(usernameCaptor.capture(), passwordCaptor.capture(), rememberCaptor.capture());
        assertEquals(encodedUsername, usernameCaptor.getValue());
        assertTrue(rememberCaptor.getValue());
        assertTrue(passwordEncrypt.checkPassword("password", passwordCaptor.getValue()));

        PowerMockito.verifyStatic();
        Application.categoryScreen();
    }

    @Test
    public void loginNotInDatabase() {
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> rememberCaptor = ArgumentCaptor.forClass(Boolean.class);

        StrongPasswordEncryptor passwordEncrypt = new StrongPasswordEncryptor();

        when(Communication.login(anyString(), anyString(), anyBoolean())).thenReturn(false);

        ApplicationMethods.login("username", "password", true);
        PowerMockito.verifyStatic();
        Communication.login(usernameCaptor.capture(), passwordCaptor.capture(), rememberCaptor.capture());
        assertEquals(encodedUsername, usernameCaptor.getValue());
        assertTrue(rememberCaptor.getValue());
        assertTrue(passwordEncrypt.checkPassword("password", passwordCaptor.getValue()));

        PowerMockito.verifyNoMoreInteractions();
    }

//    @Test (expected = UnsupportedEncodingException.class)
//    public void encoding() throws Exception {
//        when(ApplicationMethods.class, "encodeUsername", "username").thenThrow(new UnsupportedEncodingException());
//        Whitebox.invokeMethod(ApplicationMethods.class, "encodeUsername", "username");
//    }

    @Test
    public void register() {
    }
}