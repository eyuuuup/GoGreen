package gogreen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransportTest {

    @Test
    public void addCycleAction() {
        assertEquals(Transport.addCycleAction(), 100);
    }

    @Test
    public void addCarAction() {
        assertEquals(Transport.addCarAction(), 25);
    }

    @Test
    public void addPlaneAction() {
        assertEquals(Transport.addPlaneAction(), 0);
    }

    @Test
    public void addPublicTransportAction() {
        assertEquals(Transport.addPublicTransportAction(), 75);
    }

    @Test
    public void addCycleActionWrong() {
        assertNotEquals(Transport.addCycleAction(), 25);
    }

    @Test
    public void addCarActionWrong() {
        assertNotEquals(Transport.addCarAction(), 100);
    }

    @Test
    public void addPlaneActionWrong() {
        assertNotEquals(Transport.addPlaneAction(), 20);
    }

    @Test
    public void addPublicTransportActionWrong() {
        assertNotEquals(Transport.addPublicTransportAction(), 57);
    }
}