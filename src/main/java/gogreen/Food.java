package gogreen;

import client.Communication;

import java.rmi.ConnectIOException;

/**
 * The food category will handle adding an action in the foodCategory.
 * And distributing the foodCategory points
 * @author Marit Radder
 * @author Erwin van Dam
 */
public class Food {
    private Food() {}

    /**
     * will receive three booleans and calculate the points for them.
     * next to this this method will calculate the amount of CO2 reduction.
     * @param veggie vegetarian
     * @param locally locally produced
     * @param bio biological
     */
    public static void addAction(boolean veggie, boolean locally, boolean bio)
            throws ConnectIOException {
        double carbonReduced = Api.carbonAmount("diets.json?size=1");
        if (veggie) {
            if (!Communication.addAction("Meat", 50, carbonReduced, 0)) {
                throw new IllegalArgumentException("you have reached the daily limit of food actions");
            }
        }

        if (locally) {
            if (!Communication.addAction("Local", 50, carbonReduced, 0)) {
                throw new IllegalArgumentException("you have reached the daily limit of food actions");
            }
        }

        if (bio) {
            if (!Communication.addAction("Biological", 50, carbonReduced, 0)) {
                throw new IllegalArgumentException("you have reached the daily limit of food actions");
            }
        }
    }
}
