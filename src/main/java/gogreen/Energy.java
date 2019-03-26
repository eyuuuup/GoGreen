package gogreen;

import client.Communication;

/**
 * Represent the Energy Category.
 * @author Gyum cho
 */
final class Energy {
    private static boolean hasSolarPanels = false;

    private Energy() {}

    public static void onload() {
        hasSolarPanels = Communication.hasSolarPanels();
    }

    /**
     * sets boolean hasSolarPanels to solarPanels.
     * @param solarPanels has solar panels
     */
    static void setHasSolarPanels(boolean solarPanels) {
        hasSolarPanels = solarPanels;
    }

    /**
     * Method for reduce energy use.
     */
    static void addReduceEnergyAction(int houseTemperature) {
        Communication.addAction("ReduceEnergy", 100);
    }

    /**
     * Method for less shower time.
     */
    static void addReduceWater(int showerTime) {
        Communication.addAction("ReduceWater", 100);
    }
}



