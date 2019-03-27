package gogreen;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApiTest {
    @Test
    public void carbonAmount() throws Exception {
        assertEquals(3, Api.carbonAmount("automobile_trips.json?distance=10"));
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
        assertEquals(0, Api.carbonAmount("automobile_trips.json?distance=null"));
    }

    @Test
    public void carbonAmountTwo() throws Exception {
        assertEquals(1081, Api.carbonAmount("flights.json?distance=100"));
    }
}