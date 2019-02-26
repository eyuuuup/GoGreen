package gogreen;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FoodCategoryTest {

    @Test
    public void getTest(){
        // make a new object and an array filled with false
        FoodCategory food = new FoodCategory();
        boolean[] output = {false, false, false};

        assertArrayEquals(output, food.getActions());
    }

    @Test
    public void getAndSetTest(){
        // make a new object and an array filled with true, and set that as actions
        FoodCategory food = new FoodCategory();
        boolean[] actions = {true, true, true};
        food.setActions(actions);

        assertArrayEquals(actions, food.getActions());
    }

    @Test(expected=NullPointerException.class)
    public void setNullTest(){
        //make a new object and set a null array
        FoodCategory food = new FoodCategory();
        boolean[] actions = null;
        food.setActions(actions);
    }

    @Test
    public void addActionAllTrue(){
        // make a test string and make a scanner, then give the scanner the test string.
        String testString = "false true true";
        Scanner sc = new Scanner(new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8)));

        // make a new foodCategory and add the actions you put in the scanner
        FoodCategory food = new FoodCategory();
        food.addAction(sc);

        boolean[] output = {true, true, true};
        assertArrayEquals(output,food.getActions());
    }

    @Test
    public void addActionAllFalse(){
        // make a test string and make a scanner, then give the scanner the test string.
        String testString = "true false false";
        Scanner sc = new Scanner(new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8)));

        // make a new foodCategory and add the actions you put in the scanner
        FoodCategory food = new FoodCategory();
        food.addAction(sc);

        boolean[] output = {false, false, false};
        assertArrayEquals(output, food.getActions());
    }

    @Test
    public void addActionMultiple(){
        // set up the first string and scanner for the first actions.
        String firstActions = "false true true";
        Scanner firstScanner = new Scanner(new ByteArrayInputStream(firstActions.getBytes(StandardCharsets.UTF_8)));

        // make the food category object and add the first actions
        FoodCategory food = new FoodCategory();
        food.addAction(firstScanner);

        // then make the second actions string and scanner
        String secondActions = "true false false";
        Scanner secondScanner = new Scanner(new ByteArrayInputStream(secondActions.getBytes(StandardCharsets.UTF_8)));

        //add the actions
        food.addAction(secondScanner);

        boolean[] output = {true, true, true};

        assertArrayEquals(output, food.getActions());

    }

    @Test
    public void twelveHourResetAllTrue(){
        // make a new array of booleans and a new FoodCategory.
        boolean[] actions = {true, true, true};
        FoodCategory food = new FoodCategory();

        food.setActions(actions);
        assertEquals(150, food.twelveHourReset());
    }

    @Test
    public void twelveHourResetAllFalse(){
        //make a new array of booleans and a new FoodCategory
        boolean[] actions = {false, false, false};
        FoodCategory food = new FoodCategory();

        food.setActions(actions);
        assertEquals(0, food.twelveHourReset());
    }
}
