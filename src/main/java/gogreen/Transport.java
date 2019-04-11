package gogreen;

import client.Communication;

import java.rmi.ConnectIOException;

/**
 * This class represents the Transport Category.
 *
 * @author Erwin van Dam
 */
public final class Transport {
    private static boolean hasElectricCar = false;

    private Transport() {
    }

    /**
     * Sets the boolean hasElectricCar to electricCar.
     *
     * @param electricCar has electric Car
     */
    public static void setHasElectricCar(boolean electricCar) {
        hasElectricCar = electricCar;
    }

    /**
     * This methods returns points for a Cycle action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     */
    static void addCycleAction(int distance) throws ConnectIOException {
        checkDistance(distance, 0, 200);
        double carbon = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        Communication.addAction("Cycle", distance * 16, carbon, 0);
    }

    /**
     * This methods returns points for a Car action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     * iff an user has an electric car, his CO2 production is set to 0.
     */
    static void addCarAction(int distance) throws ConnectIOException {
        checkDistance(distance, 0, 2500);
        double carbon = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        if (hasElectricCar) {
            Communication.addAction("Car", distance * 8, carbon, 0);
        } else {
            Communication.addAction("Car", distance * 8, 0, carbon);
        }
    }

    /**
     * This methods returns points for a Plane action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     */
    static void addPlaneAction(int distance) throws ConnectIOException {
        checkDistance(distance, 0, 15000);
        double carbonPlane = Api.carbonAmount("flights.json?distance=" + distance);
        double carbonCar = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        double carbonReduced = carbonCar - carbonPlane;
        Communication.addAction("Plane", distance / 16, carbonReduced, carbonPlane);
    }

    /**
     * This methods returns points for a Public Transport action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     */
    static void addPublicTransportAction(int distance) throws ConnectIOException {
        checkDistance(distance, 0, 2000);
        double carbonPublicTransport = Api.carbonAmount("bus_trips.json?distance=" + distance);
        double carbonCar = Api.carbonAmount("automobile_trips.json?distance=" + distance);
        double carbonReduced = carbonCar - carbonPublicTransport;
        Communication.addAction("PublicTransport", distance * 4, carbonReduced,
                carbonPublicTransport);
    }

    /**
     * Checks of the distance is acceptable.
     *
     * @param distance the distance
     * @param min      the minimum distance
     * @param max      the maximum distance
     */
    static void checkDistance(int distance, int min, int max) {
        if (distance < min) {
            throw new IllegalArgumentException("Distance is unacceptably low");
        }
        if (distance > max) {
            throw new IllegalArgumentException("Distance is unacceptably low");
        }
    }
}
