package gogreen;

import gogreen.server.ComCached;
import gogreen.actions.Energy;
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
@PrepareForTest({ComCached.class, Api.class})
public class EnergyTest {
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ComCached.class);
        PowerMockito.mockStatic(Api.class);
        Double d = Double.valueOf(100);
        PowerMockito.when(Api.class, "carbonAmount", anyString()).thenReturn(d);
    }

    @Test
    public void addReduceEnergyAction() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", false);
        Energy.addReduceEnergyAction(20, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("ReduceEnergy", 300, 300, 2000, "description");
    }

    @Test
    public void addReduceEnergyActionSolarPanels() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", true);
        Energy.addReduceEnergyAction(20, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("ReduceEnergy", 300, 2000, 0, "description");
    }

    @Test
    public void addReduceWater() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", false);
        Energy.addReduceWater(15, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("ReduceWater", 500, 500, 1500, "description");
    }

    @Test
    public void addReduceWaterSolarPanels() throws ConnectIOException {
        Whitebox.setInternalState(Energy.class, "hasSolarPanels", true);
        Energy.addReduceWater(15, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("ReduceWater", 500, 1500, 0, "description");
    }
}