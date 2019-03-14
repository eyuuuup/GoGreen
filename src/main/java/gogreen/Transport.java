package gogreen;

import client.Communication;

/**
 * This class represents the Transport Category.
 * @author Erwin van Dam
 */
final class Transport {
    private Transport() {}

    /**
     * This methods returns points for a Cycle action.
     */
    static void addCycleAction() {
        Communication.addAction("Cycle", 100);
    }

    /**
     * This methods returns points for a Car action.
     */
    static void addCarAction() {
        Communication.addAction("Car", 25);
    }

    /**
     * This methods returns points for a Plane action.
     */
    static void addPlaneAction() {
        Communication.addAction("Plane", 0);
    }

    /**
     * This methods returns points for a Public Transport action.
     */
    static void addPublicTransportAction() {
        Communication.addAction("PublicTransport", 75);
    }
}
