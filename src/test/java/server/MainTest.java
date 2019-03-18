package server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringApplication.class)
public class MainTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SpringApplication.class);
    }

    @Test
    public void main() {
        String[] args = new String[0];
        Main.main(args);
        PowerMockito.verifyStatic();
        SpringApplication.run(Main.class, args);
    }
}