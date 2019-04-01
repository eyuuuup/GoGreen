package server;

import org.junit.Test;

import static org.junit.Assert.*;

public class onLoadValuesTest {

    OnLoadValues obj= new OnLoadValues(true, false);

    @Test
    public void isElectricCar() {
        assertEquals(true, obj.isElectricCar());
    }

    @Test
    public void isSolarCar() {
        assertEquals(false, obj.isSolarCar());
    }

    @Test
    public void setElectricCar() {
        obj.setElectricCar(false);
        assertEquals(false, obj.isElectricCar());
    }

    @Test
    public void setSolarCar() {
        obj.setSolarCar(true);
        assertEquals(true, obj.isSolarCar());
    }
}