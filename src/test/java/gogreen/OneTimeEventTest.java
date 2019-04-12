package gogreen;

import client.ComCached;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ComCached.class)
public class OneTimeEventTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(ComCached.class);
    }

    @Test
    public void addSolarPanelAction() {
        OneTimeEvent.addSolarPanelAction();
        PowerMockito.verifyStatic();
        ComCached.addAction("SolarPanel", 2000, 0, 0);
    }

    @Test
    public void addElectricCarAction() {
        OneTimeEvent.addElectricCarAction();
        PowerMockito.verifyStatic();
        ComCached.addAction("ElectricCar", 3000, 0, 0);
    }

    @Test
    public void addEvGroupAction() {
        OneTimeEvent.addEvGroupAction();
        PowerMockito.verifyStatic();
        ComCached.addAction("EvGroup", 1000, 0, 0);
    }
}