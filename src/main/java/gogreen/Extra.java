package gogreen;

import client.Communication;

/**
 * Representation of the ExtraCategory.
 * @author Gyum cho
 */
final class Extra {
    private Extra() {}

    /**
     * Method for cleaning your surrounding.
     * gives a carbon reduction of 10 kg because of approximately 20 bottles per clean up.
     */
    static void addCleanSurroundingAction(String description) {
        Communication.addAction("CleanSurrounding", 100, 5, 0, description);
    }

    /**
     * Method for recycling something.
     * gives a carbon reduction of 1 kg because 1 bottle causes 0.5 kg CO2, as int that is 1.
     */
    static void addRecycleAction(String description) {
        Communication.addAction("Recycle", 50, 1, 0, description);
    }
}