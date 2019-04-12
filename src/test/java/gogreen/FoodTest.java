package gogreen;

import client.ComCached;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.rmi.ConnectIOException;

import static org.mockito.Matchers.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ComCached.class, Api.class})
public class FoodTest {
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ComCached.class);
        PowerMockito.mockStatic(Api.class);
        Double d = Double.valueOf(100);
        PowerMockito.when(Api.class, "carbonAmount", anyString()).thenReturn(d);
        PowerMockito.when(ComCached.addAction(anyString(), anyInt(), anyDouble(), anyDouble())).thenReturn(true);
    }

    @Test
    public void addActionAllTrue() throws ConnectIOException {
        Food.addAction(true, true, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Meat", 50, 100, 0);
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyStatic();
        ComCached.addAction("Local", 50, 100, 0);
    }

    @Test
    public void addActionAllFalse() throws ConnectIOException{
        Food.addAction(false, false, false);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionOnlyMeatTrue() throws ConnectIOException{
        Food.addAction(true, false, false);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Meat", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionOnlyBioTrue() throws ConnectIOException{
        Food.addAction(false, false, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionMeatAndBioTrue() throws ConnectIOException{
        Food.addAction(true, false, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Meat", 50, 100, 0);
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionLocalAndBioTrue() throws ConnectIOException{
        Food.addAction(false, true, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Local", 50, 100, 0);
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test (expected = IllegalArgumentException.class)
    public void addActionAllError() throws Exception {
        PowerMockito.when(ComCached.addAction("Meat", 50, 100, 0)).thenReturn(false);
        Food.addAction(true, true, true);
    }

    @Test (expected = IllegalArgumentException.class)
    public void addActionAllErrorTwo() throws Exception {
        PowerMockito.when(ComCached.addAction("Biological", 50, 100, 0)).thenReturn(false);
        Food.addAction(true, true, true);
    }

    @Test (expected = IllegalArgumentException.class)
    public void addActionAllErrorThree() throws Exception {
        PowerMockito.when(ComCached.addAction("Local", 50, 100, 0)).thenReturn(false);
        Food.addAction(true, true, true);
    }
}