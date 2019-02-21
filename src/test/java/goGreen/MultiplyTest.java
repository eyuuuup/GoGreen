package goGreen;

import org.junit.Assert;
import org.junit.Test;

public class MultiplyTest {

    @Test
    public void testMultiplyValid() {

        int grade = Multiply.multiplyInt(3,5);

        Assert.assertEquals(15, grade);
    }

    @Test
    public void testMultiplyNotvalid() {

        int grade = Multiply.multiplyInt(-4,4);

        Assert.assertEquals(-16, grade);
    }

}