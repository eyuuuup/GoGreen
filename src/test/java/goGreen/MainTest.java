package goGreen;

import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    @Test
    public void testMultiplyValid() {

        int grade = Main.multiplyInt(3,5);

        Assert.assertEquals(15, grade);
    }

}