package gogreen;

/**
 * This class represents the Transport Category.
 * @author Erwin van Dam
 */
public final class Transport {

    /**
     * Overwrites the default constructor.
     */
    private Transport() { }

    /**
     * This methods returns points for a Cycle action.
     * @return points
     */
    public static int addCycleAction() {
        final int points = 100;
        return points;
    }

    /**
     * This methods returns points for a Car action.
     * @return points
     */
    public static int addCarAction() {
        final int points = 25;
        return points;
    }

    /**
     * This methods returns points for a Plane action.
     * @return points
     */
    public static int addPlaneAction() {
        final int points = 0;
        return points;
    }

    /**
     * This methods returns points for a Public Transport action.
     * @return points
     */
    public static int addPublicTransportAction() {
        final int points = 75;
        return points;
    }
}
