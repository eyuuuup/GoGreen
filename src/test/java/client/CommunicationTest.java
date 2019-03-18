package client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

public class CommunicationTest {
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
    }

    @Test
    public void register() {
        Communication.register("Username", "Password", true);
    }

    @Test
    public void login() {
        Communication.login("Username", "Password", true);
    }

    @Test
    public void silentLogin() {
        Communication.silentLogin();
    }

    @Test
    public void addAction() {
        Communication.addAction("Action", 100);
    }

    @Test
    public void addActionLoggedIn() {
        Whitebox.setInternalState( Communication.class, "token", "token");
        Communication.addAction("Action", 100);
    }

    @Test
    public void logout() {
        Communication.logout();
    }

    @Test
    public void isLoggedIn() throws Exception {
        Whitebox.invokeMethod(Communication.class, "isLoggedIn");
    }

    @Test
    public void getLastThreeActions() {
        Communication.getLastThreeActions();
    }

    @Test
    public void getLastThreeActionsLoggedIn() throws IllegalAccessException {
        Whitebox.setInternalState( Communication.class, "token", "token");
        Communication.getLastThreeActions();
    }
}