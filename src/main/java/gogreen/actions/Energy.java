package gogreen.actions;

import gogreen.server.ComCached;
import gogreen.Api;

import java.rmi.ConnectIOException;

/**
 * Represent the Energy Category.
 *
 * @author Gyum cho
 */
public final class Energy {
    private static boolean hasSolarPanels = false;

    private Energy() {
    }

    /**
     * sets boolean hasSolarPanels to solarPanels.
     *
     * @param solarPanels has solar panels
     */
    public static void setHasSolarPanels(boolean solarPanels) {
        hasSolarPanels = solarPanels;
    }

    /**
     * Method for reduced energy usage due to a lower house temperature.
     */
    public static void addReduceEnergyAction(int houseTemperature, String description) throws ConnectIOException {
        int temperatureDiff = 23 - houseTemperature;
        //average area of house * average  Specific heat capacity of house * energy used in normal day(1min)
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + 18.24);
        double carbonReduced = carbon * temperatureDiff;
        double carbonProduced = carbon * houseTemperature;

        if (hasSolarPanels) {
            ComCached.addAction("ReduceEnergy", 100 * temperatureDiff, carbonProduced, 0, description);
        } else {
            ComCached.addAction("ReduceEnergy", 100 * temperatureDiff, carbonReduced, carbonProduced, description);
        }
    }

    /**
     * Method for less shower time.
     */
    public static void addReduceWater(int showerTime, String description) throws ConnectIOException {
        int timeDiff = 20 - showerTime;
        //normal time spent of shower * normal water use during shower (1min) * normal energy contain by water(1KG)
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + 1.84);
        double carbonReduced = timeDiff * carbon;
        double carbonProduced = showerTime * carbon;

        if (hasSolarPanels) {
            ComCached.addAction("ReduceWater", 100 * timeDiff, carbonProduced, 0, description);
        } else {
            ComCached.addAction("ReduceWater", 100 * timeDiff, carbonReduced, carbonProduced, description);
        }
    }
}



