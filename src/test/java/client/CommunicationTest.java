package client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Communication.class)
public class CommunicationTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
    }

    @Test
    public void register() throws Exception {
        PowerMockito.doReturn(true).when(Communication.class, "submit", "Username", "Password", true, "/login");
        Communication.register("Username", "Password", true);
    }

    @Test
    public void login() throws Exception{
        PowerMockito.doReturn(true).when(Communication.class, "submit", "Username", "Password", true, "/login");
        Communication.login("Username", "Password", true);
    }

    @Test
    public void silentLogin() throws Exception{
        PowerMockito.doReturn(true).when(Communication.class, "silentLogin");
        Communication.silentLogin();
    }

    @Test
    public void addAction() throws Exception{
        PowerMockito.doReturn(true).when(Communication.class, "addAction", "Action", 100);
        Communication.addAction("Action", 100);
    }
}