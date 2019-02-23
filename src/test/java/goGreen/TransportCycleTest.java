package goGreen;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransportCycleTest {

    @Test
    public void addAction() {
        assertEquals(100, TransportCycle.addAction());
    }

    @Test
    public void addActionWrong() {
        assertNotEquals(110, TransportCycle.addAction());
    }
}