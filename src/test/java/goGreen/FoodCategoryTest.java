package goGreen;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class FoodCategoryTest {
//    @Test
//    public void display(){
//        how to test this? suggestions?
//    }

    @Test
    public void testAddAction(){
        boolean[] actions = new boolean[3];
        boolean[] output = {true, true, true};

        String input = "true true true";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        System.setIn(in);
        assertArrayEquals(output, FoodCategory.addAction(actions));
    }

    @Test
    public void testGetPoints(){
        boolean[] actions = {true, true, true};

        assertEquals(150, FoodCategory.getPoints(actions));
    }

    @Test
    public void testTwelveHourReset(){
        boolean[] actions = {true, true, true};
        boolean[] output = new boolean[3];

        assertArrayEquals(output, FoodCategory.twelveHourReset(actions));
    }

    @Test
    public void testTwelveHourResetGetScore(){
        boolean[] actions = {true, true, true};
        FoodCategory.twelveHourReset(actions);
        assertEquals(150, FoodCategory.getFoodScore());
    }



}