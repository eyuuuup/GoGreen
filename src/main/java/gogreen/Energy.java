package gogreen;

import client.Communication;

import java.rmi.ConnectIOException;

/**
 * Represent the Energy Category.
 * @author Gyum cho
 */
public final class Energy {
    private static boolean hasSolarPanels = false;

    private Energy() {}

    /**
     * sets boolean hasSolarPanels to solarPanels.
     * @param solarPanels has solar panels
     */
    public static void setHasSolarPanels(boolean solarPanels) {
        hasSolarPanels = solarPanels;
    }

    /**
     * Method for reduced energy usage due to a lower house temperature.
     */
    static void addReduceEnergyAction(int houseTemperature) throws ConnectIOException {
        int temperatureDiff = 23 - houseTemperature;
        double megaJoules = temperatureDiff * 18.24;
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + megaJoules);
        double carbonProduced = Api.carbonAmount("electricity_uses.json?energy=" + houseTemperature * 100);
        if (hasSolarPanels) {
            Communication.addAction("ReduceEnergy", 100 * temperatureDiff, carbon, 0);
        } else {
            Communication.addAction("ReduceEnergy", 100 * temperatureDiff, 0, carbonProduced);
        }
    }

    /**
     * Method for less shower time.
     */
    static void addReduceWater(int showerTime) throws ConnectIOException {
        int timeDiff = 20 - showerTime;
        double megaJoules = showerTime * 162.4;
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + megaJoules);
        double carbonProduced = Api.carbonAmount("electricity_uses.json?energy=" + showerTime * 100);
        if (hasSolarPanels) {
            Communication.addAction("ReduceWater", 100 * timeDiff, carbon, 0);
        } else {
            Communication.addAction("ReduceWater", 100 * timeDiff, 0, carbonProduced);
        }
    }
}



