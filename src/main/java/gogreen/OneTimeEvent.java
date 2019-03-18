package gogreen;

import client.Communication;

/**
 * This class represents the one time events.
 * @author Gyum cho
 */
final class OneTimeEvent {
    private OneTimeEvent() {}

    /**
     * Method for installing a solar panel.
     */
    static void addSolarPanelAction() {
        Communication.addAction("SolarPanel", 1000);
    }

    /**
     * Method for buying an electric car.
     */
    static void addElectricCarAction() {
        Communication.addAction("ElectricCar", 1000);
    }

    /**
     * Method for signing into an environment group.
     */
    static void addEvGroupAction() {
        Communication.addAction("EvGroup", 1000);
    }
}
