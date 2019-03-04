package gogreen;

import java.util.Scanner;

/**
 * The food category will handle adding an action in the foodCategory.
 * And distributing the foodCategory points
 * @author Marit Radder
 * @author Erwin van Dam
 */
public class FoodCategory {
    private static boolean[] actions;

    /**
     *The foodCategory constructor, which makes an array of false booleans which are the actions.
     */
    public FoodCategory() {
        actions = new boolean[] {false, false, false};
    }

    /**
     * gets the actions array.
     * @return actions, it returns the actions array.
     */
    public static boolean[] getActions() {
        return actions;
    }

    /**
     * Sets boolean[] actions to newActions.
     * @param newActions the new boolean
     * @throws NullPointerException if newActions = null
     */
    public static void setActions(boolean[] newActions) throws NullPointerException {
        // null check
        if (newActions == null) {
            throw new NullPointerException("Actions equals null");
        }
        actions = newActions;
    }


    /**
     * The user will give an input, those will be stored in the actions of the category.
     * @param sc is the scanner for the user input.
     */
    public static void addAction(Scanner sc) {
        //ask questions, later will become buttons
        System.out.println("Did it contain meat? (true / false)");
        actions[0] |= !Boolean.parseBoolean(sc.next());

        System.out.println("Was it biological (true / false)");
        actions[1] |= Boolean.parseBoolean(sc.next());

        System.out.println("Was it locally produced(true / false)");
        actions[2] |= Boolean.parseBoolean(sc.next());
    }

    /**
     * this method looks at your actions array and will add 50 points for every true in that array.
     * @return food score, is an integer which represents the points you got from your actions
     */
    public static int twelveHourReset() {
        int foodScore = 0;
        // add points to total score
        for (boolean action: actions) {
            // for every good action add 50 points
            if (action) {
                foodScore += 50;
            }
        }
        actions = new boolean[] {false, false, false};
        return foodScore;
    }
}
