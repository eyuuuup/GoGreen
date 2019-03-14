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
     */
    static void addCleanSurroundingAction() {
        Communication.addAction("CleanSurrounding", 100);
    }

    /**
     * Method for recycling something.
     */
    static void addRecycleAction() {
        Communication.addAction("Recycle", 100);
    }
}