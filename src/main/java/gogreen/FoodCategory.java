package gogreen;

import client.Communication;

/**
 * The food category will handle adding an action in the foodCategory.
 * And distributing the foodCategory points
 * @author Marit Radder
 * @author Erwin van Dam
 */
public class FoodCategory {
    private FoodCategory() {}

    /**
     * will receive three booleans and calculate the points for them.
     * @param veggie vegetarian
     * @param locally locally produced
     * @param bio biological
     */
    public static void addAction(boolean veggie, boolean locally, boolean bio) {
        if (veggie) {
            Communication.addAction("Meat", 50);
        }

        if (locally) {
            Communication.addAction("Local", 50);
        }

        if (bio) {
            Communication.addAction("Biological", 50);
        }
    }
}
