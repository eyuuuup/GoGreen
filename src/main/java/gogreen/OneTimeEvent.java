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
        Communication.addAction("SolarPanel", 2000, 0, 0);
        Energy.setHasSolarPanels(true);
    }

    /**
     * Method for buying an electric car.
     */
    static void addElectricCarAction() {
        Communication.addAction("ElectricCar", 3000, 0, 0);
        Transport.setHasElectricCar(true);
    }

    /**
     * Method for signing into an environment group.
     */
    static void addEvGroupAction() {
        Communication.addAction("EvGroup", 1000, 0, 0);
    }
}
