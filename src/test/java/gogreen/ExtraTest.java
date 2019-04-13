package gogreen;

import gogreen.server.ComCached;
import gogreen.actions.Extra;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ComCached.class)
public class ExtraTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(ComCached.class);
    }

    @Test
    public void addCleanSurroundingAction() {
        Extra.addCleanSurroundingAction("description");
        PowerMockito.verifyStatic();
        ComCached.addAction("CleanSurrounding", 100, 5, 0, "description");
    }

    @Test
    public void addRecycleAction() {
        Extra.addRecycleAction("description");
        PowerMockito.verifyStatic();
        ComCached.addAction("Recycle", 50, 1, 0, "description");
    }
}