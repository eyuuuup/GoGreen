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
public class TransportTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
    }

    @Test
    public void addCycleAction() {
        Transport.addCycleAction();
        PowerMockito.verifyStatic();
        Communication.addAction("Cycle", 100);
    }

    @Test
    public void addCarAction() {
        Transport.addCarAction();
        PowerMockito.verifyStatic();
        Communication.addAction("Car", 25);
    }

    @Test
    public void addPlaneAction() {
        Transport.addPlaneAction();
        PowerMockito.verifyStatic();
        Communication.addAction("Plane", 0);
    }

    @Test
    public void addPublicTransportAction() {
        Transport.addPublicTransportAction();
        PowerMockito.verifyStatic();
        Communication.addAction("PublicTransport", 75);
    }
}