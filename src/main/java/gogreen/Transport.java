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

    /**
     * This method is called when the application starts.
     * It checks whether the user has an electric car.
     */
    static void onLoad() {
        hasElectricCar = Communication.hasElectricCar();
    }

    /**
     * Sets the boolean hasElectricCar to electricCar.
     * @param electricCar has electric Car
     */
    static void setHasElectricCar(boolean electricCar) {
        hasElectricCar = electricCar;
    }

    /**
     * This methods returns points for a Cycle action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     */
    static void addCycleAction(int distance) throws ConnectIOException {
        int carbon = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
        Communication.addAction("Cycle", distance * 16, carbon * -1, 0);
    }

    /**
     * This methods returns points for a Car action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     * iff an user has an electric car, his CO2 production is set to 0.
     */
    static void addCarAction(int distance) throws ConnectIOException {
        int carbon = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
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
        int carbonPlane = Api.CarbonAmount("flights.json?distance=" + distance);
        int carbonCar = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
        int carbonReduced = carbonCar - carbonPlane;
        Communication.addAction("Plane", distance / 16, carbonReduced, carbonPlane);
    }

    /**
     * This methods returns points for a Public Transport action.
     * Next to this the method calculates the CO2 reduction using Brighter planet.
     */
    static void addPublicTransportAction(int distance) throws ConnectIOException {
        int carbonPublicTransport = Api.CarbonAmount("bus_trips.json?distance=" + distance);
        int carbonCar = Api.CarbonAmount("automobile_trips.json?distance=" + distance);
        int carbonReduced = carbonCar - carbonPublicTransport;
        Communication.addAction("PublicTransport", distance * 4, carbonReduced,
                carbonPublicTransport);
    }
}
