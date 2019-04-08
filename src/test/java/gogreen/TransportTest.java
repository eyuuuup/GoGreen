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
public class TransportTest {
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Communication.class);
        PowerMockito.mockStatic(Api.class);
        Double d = Double.valueOf(100);
        PowerMockito.when(Api.class, "carbonAmount", anyString()).thenReturn(d);
    }

    @Test
    public void addCycleAction() throws ConnectIOException {
        Transport.addCycleAction(10, "test");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=10");
        PowerMockito.verifyStatic();
        Communication.addAction("Cycle", 160, 100, 0);
    }

    @Test
    public void addCarAction() throws ConnectIOException {
        Whitebox.setInternalState(Transport.class, "hasElectricCar", false);
        Transport.addCarAction(10, "test");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=10");
        PowerMockito.verifyStatic();
        Communication.addAction("Car", 80, 0, 100);
    }

    @Test
    public void addCarActionElectricCar() throws ConnectIOException {
        Whitebox.setInternalState(Transport.class, "hasElectricCar", true);
        Transport.addCarAction(10, "test");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=10");
        PowerMockito.verifyStatic();
        Communication.addAction("Car", 80, 100, 0);
    }

    @Test
    public void addPlaneAction() throws ConnectIOException {
        Transport.addPlaneAction(16, "test");
        PowerMockito.verifyStatic();
        Api.carbonAmount("flights.json?distance=16");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=16");
        PowerMockito.verifyStatic();
        Communication.addAction("Plane", 1, 0, 100);
    }

    @Test
    public void addPublicTransportAction() throws ConnectIOException {
        Transport.addPublicTransportAction(10, "test");
        PowerMockito.verifyStatic();
        Api.carbonAmount("bus_trips.json?distance=10");
        PowerMockito.verifyStatic();
        Api.carbonAmount("automobile_trips.json?distance=10");
        PowerMockito.verifyStatic();
        Communication.addAction("PublicTransport", 40, 0, 100);
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