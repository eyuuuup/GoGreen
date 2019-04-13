package gogreen.actions;

import gogreen.Api;
import gogreen.server.ComCached;

import java.rmi.ConnectIOException;

/**
 * The transport category will handle adding an action in the transport category.
 * And distributing the transport category points
 */
public final class Transport {
    private static boolean hasElectricCar = false;

    /**
     * private empty constructor.
     */
    private Transport() {
    }

    /**
     * Sets the boolean hasElectricCar to electricCar.
     * @param electricCar has electric Car
     */
    public static void setHasElectricCar(boolean electricCar) {
        hasElectricCar = electricCar;
    }

    /**
     * This methods returns points for a Cycle action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     * @param distance the distance
     * @param description the description
     * @throws ConnectIOException if we can't connect with the api we throw an error
     */
    public static void addCycleAction(int distance, String description)
            throws ConnectIOException {
        checkDistance(distance, 1, 200);
        double carbon = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        ComCached.addAction("Cycle", distance * 4, carbon, 0, description);
    }

    /**
     * This methods returns points for a Car action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     * iff an user has an electric car, his CO2 production is set to 0.
     * @param distance the distance
     * @param description the description
     * @throws ConnectIOException if we can't connect with the API we throw an error
     */
    public static void addCarAction(int distance, String description)
            throws ConnectIOException {
        checkDistance(distance, 1, 2500);
        double carbon = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        if (hasElectricCar) {
            ComCached.addAction("Car", distance / 4, carbon, 0, description);
        } else {
            ComCached.addAction("Car", distance / 32, 0, carbon, description);
        }
    }

    /**
     * This methods returns points for a Plane action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     * @param distance the distance
     * @param description the description
     * @throws ConnectIOException if we can't connect with the API we throw an error
     */
    public static void addPlaneAction(int distance, String description)
            throws ConnectIOException {
        checkDistance(distance, 40, 15000);
        double carbonPlane = Api.carbonAmount("flights.json?distance=" + distance);
        double carbonCar = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        double carbonReduced = carbonCar - carbonPlane;
        ComCached.addAction("Plane", (15000 - distance) / 100,
                carbonReduced, carbonPlane, description);
    }

    /**
     * This methods returns points for a Public Transport action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     * @param distance the distance
     * @param description the description
     * @throws ConnectIOException if we can't connect with the API we throw an error
     */
    public static void addPublicTransportAction(int distance, String description)
            throws ConnectIOException {
        checkDistance(distance, 1, 2000);
        double carbonPublicTransport = Api.carbonAmount("bus_trips.json?distance=" + distance);
        double carbonCar = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        double carbonReduced = carbonCar - carbonPublicTransport;
        ComCached.addAction("PublicTransport", distance / 4,
                carbonReduced, carbonPublicTransport, description);
    }

    /**
     * Checks of the distance is acceptable.
     * @param distance the distance
     * @param min the minimum distance
     * @param max the maximum distance
     */
    public static void checkDistance(int distance, int min, int max) {
        if (distance < min) {
            throw new IllegalArgumentException("Distance is unacceptably low");
        }
        if (distance > max) {
            throw new IllegalArgumentException("Distance is unacceptably low");
        }
    }
}
