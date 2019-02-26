package gogreen;

import java.util.Scanner;

/**
 * This Class represents the Food Category.
 */
public final class FoodCategory {

    /**
     * Overwrites the default Constructor.
     */
    private FoodCategory() { }

    /**
     * This method adds an food action.
     * @param sc Input scanner
     * @param actions action array
     */
    public static void addAction(final Scanner sc, final boolean[] actions) {
        //ask questions, later will become buttons
        System.out.println("Did it contain meat? (true / false)");
        actions[0] |= !Boolean.parseBoolean(sc.next());

        System.out.println("Was it biological (true / false)");
        actions[1] |= Boolean.parseBoolean(sc.next());

        System.out.println("Was it locally produced(true / false)");
        actions[2] |= Boolean.parseBoolean(sc.next());
    }

    /**
     * This method calculates the points from the last 12 hours.
     * @param actions action array
     * @return points
     */
    public static int twelveHourReset(final boolean[] actions) {
        int foodScore = 0;
        final int points = 50;
        // add points to total score
        for (boolean action: actions) {
            if (action) {
                foodScore += points;
            }
        }
        return foodScore;
    }
}
