package gogreen;

import java.util.Scanner;

/**
 * The food category will handle adding an action in the foodCategory.
 * And distributing the foodCategory points
 * @author Marit Radder
 * @author Erwin van Dam
 */
public class FoodCategory {
    /**
     * The user will give an input, those will be stored in the actions of the category.
     * @param sc is the scanner for the user input.
     */
    public static void addAction(Scanner sc) {
        //ask questions, later will become buttons
        System.out.println("Did it contain meat? (true / false)");
        if(!Boolean.parseBoolean(sc.next()))Communication.addAction("Meat", 50);

        System.out.println("Was it biological (true / false)");
        if(Boolean.parseBoolean(sc.next()))Communication.addAction("Biological", 50);

        System.out.println("Was it locally produced(true / false)");
        if(Boolean.parseBoolean(sc.next()))Communication.addAction("Locally", 50);
    }
}
