package gogreen;

import client.Communication;

import java.rmi.ConnectIOException;

/**
 * This class represents the Transport Category.
 * @author Erwin van Dam
 */
final class Transport {
    //this class uses addAction(String action, int value, int reduction, int carbon)
    private static boolean hasElectricCar = false;

    private Transport() {}

    static void onLoad() {
        hasElectricCar = Communication.hasElectricCar();
    }

    /**
     * This methods returns points for a Cycle action.
     */
    static void addCycleAction(int distance) throws ConnectIOException {
        int carbon = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
        Communication.addAction("Cycle", distance * 16);
        Communication.addAction("Cycle", distance * 16, carbon * -1, 0);
    }

    /**
     * This methods returns points for a Car action.
     */
    static void addCarAction(int distance) throws ConnectIOException {
        int carbon = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
        Communication.addAction("Car", distance * 8);
        if (hasElectricCar) {
            Communication.addAction("Car", distance * 8, carbon, 0);
        } else {
            Communication.addAction("Car", distance * 8, 0, carbon);
        }
    }

    /**
     * This methods returns points for a Plane action.
     */
    static void addPlaneAction(int distance) throws ConnectIOException {
        int carbonPlane = Api.CarbonAmount("flights.json?distance=" + distance);
        int carbonCar = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
        int carbonReduced = carbonCar - carbonPlane;
        Communication.addAction("Plane", distance / 16, carbonReduced, carbonPlane);
        Communication.addAction("Plane", distance / 16);
    }

    /**
     * This methods returns points for a Public Transport action.
     */
    static void addPublicTransportAction(int distance) throws ConnectIOException {
        int carbonPublicTransport = Api.CarbonAmount("bus_trips.json?distance=" + distance);
        int carbonCar = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
        int carbonReduced = carbonCar - carbonPublicTransport;
        Communication.addAction("PublicTransport", distance * 4, carbonReduced,
                carbonPublicTransport);
        Communication.addAction("PublicTransport", distance * 4);
    }
}
