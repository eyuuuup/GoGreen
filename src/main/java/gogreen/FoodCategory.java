package gogreen;

import client.Communication;

/**
 * The food category will handle adding an action in the foodCategory.
 * And distributing the foodCategory points
 * @author Marit Radder
 * @author Erwin van Dam
 */
public class FoodCategory {


    /**
     * will receive three booleans and calculate the points for them.
     * @param veggie vegetarian
     * @param locally locally produced
     * @param bio biological
     */
    public static void addAction(Boolean veggie, boolean locally, boolean bio) {
        //ask questions, later will become buttons
        if (veggie) {
            System.out.println("veggie");
            Communication.addAction("Meat", 50);
        }

        if (locally) {
            System.out.println("locally");
            Communication.addAction("Biological", 50);
        }

        if (bio) {
            System.out.println("bio");
            Communication.addAction("Locally", 50);
        }
    }
}
