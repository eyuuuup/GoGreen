package gogreen;

/**
 * This class represents the Transport Category.
 * @author Erwin van Dam
 */
public final class Transport {

    /**
     * This methods returns points for a Cycle action.
     */
    public static void addCycleAction() {
        Communication.addAction("Cycle", 100);
    }

    /**
     * This methods returns points for a Car action.
     */
    public static void addCarAction() {
        Communication.addAction("Car", 25);
    }

    /**
     * This methods returns points for a Plane action.
     */
    public static void addPlaneAction() {
        Communication.addAction("Plane", 0);
    }

    /**
     * This methods returns points for a Public Transport action.
     */
    public static void addPublicTransportAction() {
        Communication.addAction("PublicTransport", 75);
    }
}
