package gogreen;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.rmi.ConnectIOException;

import static org.junit.Assert.*;

public class ApiTest {
    @Before
    public void setup() {
        Whitebox.setInternalState(Api.class, "site", "http://impact.brighterplanet.com/");
    }

    @Test
    public void carbonAmount() throws Exception {
        assertEquals(3, Api.carbonAmount("automobile_trips.json?distance=10"), 1);
    }

    @Test (expected = JSONException.class)
    public void carbonAmountWrongParams() throws Exception {
        Api.carbonAmount("params");
    }

    @Test (expected = JSONException.class)
    public void carbonAmountWrongParamsTwo() throws Exception {
        Api.carbonAmount("auto.json?distance=100");
    }

    @Test
    public void carbonAmountWrongDistance() throws Exception {
        assertEquals(0, Api.carbonAmount("automobile_trips.json?distance=null"), 0);
    }

    @Test
    public void carbonAmountTwo() throws Exception {
        assertEquals(1081, Api.carbonAmount("flights.json?distance=100"), 1);
    }

    @Test (expected = ConnectIOException.class)
    public void carbonAmountException() throws ConnectIOException {
        Whitebox.setInternalState(Api.class, "site", "d");
        Api.carbonAmount("flights.json?distance=100");
    }
}