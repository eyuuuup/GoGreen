package gogreen;

import client.Communication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Communication.class)
public class FoodCategoryTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Communication.class);
    }

    @Test
    public void addActionAllTrue() {
        FoodCategory.addAction(true, true, true);
        PowerMockito.verifyStatic();
        Communication.addAction("Meat", 50);
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50);
        PowerMockito.verifyStatic();
        Communication.addAction("Local", 50);
    }

    @Test
    public void addActionAllFalse() {
        FoodCategory.addAction(false, false, false);
        PowerMockito.verifyStatic();
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionOnlyMeatTrue() {
        FoodCategory.addAction(true, false, false);
        PowerMockito.verifyStatic();
        Communication.addAction("Meat", 50);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionOnlyBioTrue() {
        FoodCategory.addAction(false, false, true);
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionMeatAndBioTrue() {
        FoodCategory.addAction(true, false, true);
        PowerMockito.verifyStatic();
        Communication.addAction("Meat", 50);
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50);
        PowerMockito.verifyNoMoreInteractions();
    }

    @Test
    public void addActionLocalAndBioTrue() {
        FoodCategory.addAction(false, true, true);
        PowerMockito.verifyStatic();
        Communication.addAction("Local", 50);
        PowerMockito.verifyStatic();
        Communication.addAction("Biological", 50);
        PowerMockito.verifyNoMoreInteractions();
    }
}