package gogreen;

import client.Communication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.rmi.ConnectIOException;

import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Communication.class, Api.class})
public class FoodTest {
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Communication.class);
        PowerMockito.mockStatic(Api.class);
        PowerMockito.when(Api.class, "carbonAmount", anyString()).thenReturn(100);
    }

    @Test
    public void addActionAllTrue() throws ConnectIOException {
        Food.addAction(true, true, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        Communication.addAction("Meat", 50, 100, 0);
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyStatic();
        Communication.addAction("Local", 50, 100, 0);
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
        Communication.addAction("Meat", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionOnlyBioTrue() throws ConnectIOException{
        Food.addAction(false, false, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionMeatAndBioTrue() throws ConnectIOException{
        Food.addAction(true, false, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        Communication.addAction("Meat", 50, 100, 0);
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionLocalAndBioTrue() throws ConnectIOException{
        Food.addAction(false, true, true);
        PowerMockito.verifyStatic();
        Api.carbonAmount("diets.json?size=1");
        PowerMockito.verifyStatic();
        Communication.addAction("Local", 50, 100, 0);
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50, 100, 0);
        PowerMockito.verifyNoMoreInteractions();
    }
}