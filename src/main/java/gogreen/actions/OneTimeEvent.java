package gogreen.actions;

import gogreen.server.ComCached;

/**
 * This class represents the one time events.
 *
 * @author Gyum cho
 */
public final class OneTimeEvent {
    private OneTimeEvent() {
    }

    /**
     * Method for installing a solar panel.
     */
    public static void addSolarPanelAction() {
        ComCached.addAction("SolarPanel", 2000, 0, 0);
        Energy.setHasSolarPanels(true);
    }

    /**
     * Method for buying an electric car.
     */
    public static void addElectricCarAction() {
        ComCached.addAction("ElectricCar", 3000, 0, 0);
        Transport.setHasElectricCar(true);
    }

    /**
     * Method for signing into an environment group.
     */
    public static void addEvGroupAction() {
        ComCached.addAction("EvGroup", 1000, 0, 0);
    }
}
