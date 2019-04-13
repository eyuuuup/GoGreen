package gogreen;

import gogreen.server.ComCached;
import gogreen.actions.Transport;
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
public class TransportTest {
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ComCached.class);
        PowerMockito.mockStatic(Api.class);
        Double d = Double.valueOf(100);
        PowerMockito.when(Api.class, "carbonAmount", anyString()).thenReturn(d);
    }

    @Test
    public void addCycleAction() throws ConnectIOException {
        Transport.addCycleAction(10, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=10");
        PowerMockito.verifyStatic();
        ComCached.addAction("Cycle", 40, 100, 0, "description");
    }

    @Test
    public void addCarAction() throws ConnectIOException {
        Whitebox.setInternalState(Transport.class, "hasElectricCar", false);
        Transport.addCarAction(32, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=32");
        PowerMockito.verifyStatic();
        ComCached.addAction("Car", 1, 0, 100, "description");
    }

    @Test
    public void addCarActionElectricCar() throws ConnectIOException {
        Whitebox.setInternalState(Transport.class, "hasElectricCar", true);
        Transport.addCarAction(32, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=32");
        PowerMockito.verifyStatic();
        ComCached.addAction("Car", 8, 100, 0, "description");
    }

    @Test
    public void addPlaneAction() throws ConnectIOException {
        Transport.addPlaneAction(14000, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("flights.json?distance=14000");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=14000");
        PowerMockito.verifyStatic();
        ComCached.addAction("Plane", 10, 0, 100, "description");
    }

    @Test
    public void addPublicTransportAction() throws ConnectIOException {
        Transport.addPublicTransportAction(4, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("bus_trips.json?distance=4");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=4");
        PowerMockito.verifyStatic();
        ComCached.addAction("PublicTransport", 1, 0, 100, "description");
    }

    @Test
    public void checkDistance() {
        Transport.checkDistance(100, 0, 200);
    }

    @Test (expected = IllegalArgumentException.class)
    public void checkDistanceToLong() {
        Transport.checkDistance(100, 0, 50);
    }

    @Test (expected = IllegalArgumentException.class)
    public void checkDistanceToShort() {
        Transport.checkDistance(100, 150, 200);
    }
}