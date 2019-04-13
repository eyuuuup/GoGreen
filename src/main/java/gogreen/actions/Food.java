package gogreen.actions;

import gogreen.Api;
import gogreen.server.ComCached;

import java.rmi.ConnectIOException;

/**
 * The food category will handle adding an action in the food category.
 * And distributing the food category points
 */
public class Food {
    /**
     * private empty constructor.
     */
    private Food() {
    }

    /**
     * will receive three booleans and calculate the points for them.
     * next to this this method will calculate the amount of CO2 reduction.
     * @param veggie  vegetarian
     * @param locally locally produced
     * @param bio     biological
     * @throws ConnectIOException if we can't connect with the api we throw an error
     */
    public static void addAction(boolean veggie, boolean locally, boolean bio, String description)
            throws ConnectIOException {
        String exception = "you have reached the daily limit of food actions";
        double carbonReduced = Api.carbonAmount("diets.json?size=1");

        // if we ate veggie we don't add the meat action
        if (veggie) {
            if (!ComCached.addAction("Meat", 50, carbonReduced, 0, description)) {
                throw new IllegalArgumentException(exception);
            }
        }

        // if we ate locally we add the locally action
        if (locally) {
            if (!ComCached.addAction("Local", 50, carbonReduced, 0, description)) {
                throw new IllegalArgumentException(exception);
            }
        }

        // if we ate bio we add the bio action
        if (bio) {
            if (!ComCached.addAction("Biological", 50, carbonReduced, 0, description)) {
                throw new IllegalArgumentException(exception);
            }
        }
    }
}
