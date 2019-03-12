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
public class OneTimeEventTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
    }

    @Test
    public void addSolarPanelAction() {
        OneTimeEvent.addSolarPanelAction();
        PowerMockito.verifyStatic();
        Communication.addAction("SolarPanel", 1000);
    }

    @Test
    public void addElectricCarAction() {
        OneTimeEvent.addElectricCarAction();
        PowerMockito.verifyStatic();
        Communication.addAction("ElectricCar", 1000);
    }

    @Test
    public void addEvGroupAction() {
        OneTimeEvent.addEvGroupAction();
        PowerMockito.verifyStatic();
        Communication.addAction("EvGroup", 1000);
    }
}