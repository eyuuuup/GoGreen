package gogreen;

import client.Communication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.rmi.ConnectIOException;

import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Communication.class, Api.class})
public class EnergyTest {
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Communication.class);
        PowerMockito.mockStatic(Api.class);
        Double d = Double.valueOf(100);
        PowerMockito.when(Api.class, "carbonAmount", anyString()).thenReturn(d);
    }

    @Test
    public void addReduceEnergyAction() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", false);
        Energy.addReduceEnergyAction(20);
        PowerMockito.verifyStatic();
        Communication.addAction("ReduceEnergy", 300, 100, 100);
    }

    @Test
    public void addReduceEnergyActionSolarPanels() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", true);
        Energy.addReduceEnergyAction(20);
        PowerMockito.verifyStatic();
        Communication.addAction("ReduceEnergy", 300, 100, 0);
    }

    @Test
    public void addReduceWater() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", false);
        Energy.addReduceWater(15);
        PowerMockito.verifyStatic();
        Communication.addAction("ReduceWater", 500, 100, 100);
    }

    @Test
    public void addReduceWaterSolarPanels() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", true);
        Energy.addReduceWater(15);
        PowerMockito.verifyStatic();
        Communication.addAction("ReduceWater", 500, 100, 0);
    }
}