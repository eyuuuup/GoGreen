package gogreen.actions;

import gogreen.server.ComCached;


/**
 * The extra category will handle adding an action in the extra category.
 * And distributing the extra category points
 */
public final class Extra {
    /**
     * private empty constructor.
     */
    private Extra() {
    }

    /**
     * Method for cleaning your surrounding.
     * Gives a carbon reduction of 10 kg because of approximately 20 bottles per clean up.
     * @param description the description
     */
    public static void addCleanSurroundingAction(String description) {
        ComCached.addAction("CleanSurrounding", 100, 5, 0, description);
    }

    /**
     * Method for recycling something.
     * Gives a carbon reduction of 1 kg because 1 bottle causes 0.5 kg CO2, as int that is 1.
     * @param description the description
     */
    public static void addRecycleAction(String description) {
        ComCached.addAction("Recycle", 50, 1, 0, description);
    }
}