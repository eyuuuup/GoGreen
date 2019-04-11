package gogreen;

import client.Communication;

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
    static void addReduceEnergyAction(int houseTemperature) throws ConnectIOException {
        int temperatureDiff = 23 - houseTemperature;
        //average area of house * average  Specific heat capacity of house * energy used in normal day(1min)
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + 18.24);
        double carbonReduced = carbon * temperatureDiff;
        double carbonProduced = carbon * houseTemperature;

        if (hasSolarPanels) {
            Communication.addAction("ReduceEnergy", 100 * temperatureDiff, carbonProduced, 0);
        } else {
            Communication.addAction("ReduceEnergy", 100 * temperatureDiff, carbonReduced, carbonProduced);
        }
    }

    /**
     * Method for less shower time.
     */
    static void addReduceWater(int showerTime) throws ConnectIOException {
        int timeDiff = 20 - showerTime;
        //normal time spent of shower * normal water use during shower (1min) * normal energy contain by water(1KG)
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + 18.4);
        double carbonReduced = timeDiff * carbon;
        double carbonProduced = showerTime * carbon;

        if (hasSolarPanels) {
            Communication.addAction("ReduceWater", 100 * timeDiff, carbonProduced, 0);
        } else {
            Communication.addAction("ReduceWater", 100 * timeDiff, carbonReduced, carbonProduced);
        }
    }
}



