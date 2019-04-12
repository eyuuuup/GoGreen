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
        PowerMockito.when(Api.class, "carbonAmount", anyString()).thenReturn(Double.valueOf(100));
        PowerMockito.when(ComCached.addAction(anyString(), anyInt(), anyDouble(), anyDouble(), anyString())).thenReturn(true);
    }

    @Test
    public void addActionAllTrue() throws ConnectIOException {
        Food.addAction(true, true, true, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Meat", 50, 100, 0, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("Local", 50, 100, 0, "description");
    }

    @Test
    public void addActionAllFalse() throws ConnectIOException{
        Food.addAction(false, false, false, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionOnlyMeatTrue() throws ConnectIOException{
        Food.addAction(true, false, false, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Meat", 50, 100, 0, "description");
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionOnlyBioTrue() throws ConnectIOException{
        Food.addAction(false, false, true, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0, "description");
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionMeatAndBioTrue() throws ConnectIOException{
        Food.addAction(true, false, true, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Meat", 50, 100, 0, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0, "description");
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionLocalAndBioTrue() throws ConnectIOException{
        Food.addAction(false, true, true, "description");
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        ComCached.addAction("Local", 50, 100, 0, "description");
        PowerMockito.verifyStatic();
        ComCached.addAction("Biological", 50, 100, 0, "description");
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test (expected = IllegalArgumentException.class)
    public void addActionAllError() throws Exception {
        PowerMockito.when(ComCached.addAction("Meat", 50, 100, 0, "description")).thenReturn(false);
        Food.addAction(true, true, true, "description");
    }

    @Test (expected = IllegalArgumentException.class)
    public void addActionAllErrorTwo() throws Exception {
        PowerMockito.when(ComCached.addAction("Biological", 50, 100, 0, "description")).thenReturn(false);
        Food.addAction(true, true, true, "description");
    }

    @Test (expected = IllegalArgumentException.class)
    public void addActionAllErrorThree() throws Exception {
        PowerMockito.when(ComCached.addAction("Local", 50, 100, 0, "description")).thenReturn(false);
        Food.addAction(true, true, true, "description");
    }
}