package gogreen;

import client.Communication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Communication.class)
public class ExtraTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
    }

    @Test
    public void addCleanSurroundingAction() {
        Extra.addCleanSurroundingAction();
        PowerMockito.verifyStatic();
        Communication.addAction("CleanSurrounding", 100, 5, 0);
    }

    @Test
    public void addRecycleAction() {
        Extra.addRecycleAction();
        PowerMockito.verifyStatic();
        Communication.addAction("Recycle", 50, 1, 0);
    }
}