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
public class EnergyTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
    }

    @Test
    public void addReduceEnergyAction() {
        Energy.addReduceEnergyAction(0);
        PowerMockito.verifyStatic();
        Communication.addAction("ReduceEnergy", 100);
    }

    @Test
    public void addReduceWater() {
        Energy.addReduceWater(0);
        PowerMockito.verifyStatic();
        Communication.addAction("ReduceWater", 100);
    }
}