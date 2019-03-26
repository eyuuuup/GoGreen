package gogreen;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApiTest {
    @Test
    public void carbonAmount() throws Exception {
        assertEquals(3, Api.CarbonAmount("automobile_trips.json?distance=10"));
    }

    @Test (expected = JSONException.class)
    public void carbonAmountWrongParams() throws Exception {
        Api.CarbonAmount("params");
    }

    @Test (expected = JSONException.class)
    public void carbonAmountWrongParamsTwo() throws Exception {
        Api.CarbonAmount("auto.json?distance=100");
    }

    @Test
    public void carbonAmountWrongDistance() throws Exception {
        assertEquals(0, Api.CarbonAmount("automobile_trips.json?distance=null"));
    }

    @Test
    public void carbonAmountTwo() throws Exception {
        assertEquals(1081, Api.CarbonAmount("flights.json?distance=100"));
    }
}