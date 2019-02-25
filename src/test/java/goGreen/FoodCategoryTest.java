package goGreen;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FoodCategoryTest {

    @Test
    void getTest(){
        FoodCategory food = new FoodCategory();
        boolean[] output = {false, false, false};

        assertArrayEquals(output, food.getActions());
    }

    @Test
    void getAndSetTest(){
        FoodCategory food = new FoodCategory();
        boolean[] actions = {true, true, true};
        food.setActions(actions);

        assertArrayEquals(actions, food.getActions());
    }

    @Test
    void addActionAllTrue(){
        String testString = "false true true";
        Scanner sc = new Scanner(new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8)));

        FoodCategory food = new FoodCategory();
        food.addAction(sc);

        boolean[] output = {true, true, true};
        assertArrayEquals(output,food.getActions());
    }

    @Test
    void addActionAllFalse(){
        String testString = "true false false";
        Scanner sc = new Scanner(new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8)));

        FoodCategory food = new FoodCategory();
        food.addAction(sc);

        boolean[] output = {false, false, false};
        assertArrayEquals(output, food.getActions());
    }

    @Test
    void twelveHourResetAllTrue(){
        boolean[] actions = {true, true, true};
        FoodCategory food = new FoodCategory();

        food.setActions(actions);
        assertEquals(150, food.twelveHourReset());
    }

    @Test
    void twelveHourResetAllFalse(){
        boolean[] actions = {false, false, false};
        FoodCategory food = new FoodCategory();

        food.setActions(actions);
        assertEquals(0, food.twelveHourReset());
    }

}

