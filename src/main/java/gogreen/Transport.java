package gogreen;

import client.Communication;

/**
 * This class represents the Transport Category.
 * @author Erwin van Dam
 */
public final class Transport {

    /**
     * This methods returns points for a Cycle action.
     */
    public static void addCycleAction() {
        Communication.addRequest("Cycle", 100);
    }

    /**
     * This methods returns points for a Car action.
     */
    public static void addCarAction() {
        Communication.addRequest("Car", 25);
    }

    /**
     * This methods returns points for a Plane action.
     */
    public static void addPlaneAction() {
        Communication.addRequest("Plane", 0);
    }

    /**
     * This methods returns points for a Public Transport action.
     */
    public static void addPublicTransportAction() {
        Communication.addRequest("PublicTransport", 75);
    }
}
