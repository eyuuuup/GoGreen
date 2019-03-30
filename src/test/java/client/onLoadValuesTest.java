package client;

import org.junit.Test;

import static org.junit.Assert.*;

public class onLoadValuesTest {

    onLoadValues obj = new onLoadValues(true, false);

    @Test
    public void emptyConstructor() {
        onLoadValues object = new onLoadValues();
        object.setElectricCar(true);
        object.setSolarCar(false);
        assertEquals(true, object.isElectricCar());
        assertEquals(false, object.isSolarCar());
    }

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