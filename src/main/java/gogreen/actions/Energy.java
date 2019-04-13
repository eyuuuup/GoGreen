package gogreen.actions;

import gogreen.Api;
import gogreen.server.ComCached;

import java.rmi.ConnectIOException;

/**
 * The energy category will handle adding an action in the energy category.
 * And distributing the energy category points
 **/

public final class Energy {
    private static boolean hasSolarPanels = false;

    /**
     * private empty constructor.
     */
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
     * method to calculate the reduced energy.
     * @param houseTemperature the house temperature
     * @param description the description
     * @throws ConnectIOException if we can't connect with the API, it throws an error
     */
    public static void addReduceEnergyAction(int houseTemperature,
                                             String description) throws ConnectIOException {

        int temperatureDiff = 23 - houseTemperature;

        //average area of house * average specific heat capacity of house
        // * energy used in normal day(1min)
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + 18.24);
        double carbonReduced = carbon * temperatureDiff;
        double carbonProduced = carbon * houseTemperature;

        // if we have solar panels we use another calculation
        if (hasSolarPanels) {
            ComCached.addAction("ReduceEnergy", 100 * temperatureDiff, carbonProduced,
                    0, description);
        } else {
            ComCached.addAction("ReduceEnergy", 100 * temperatureDiff, carbonReduced,
                    carbonProduced, description);
        }
    }

    /**
     * Method to calculate the carbon reduced with showering.
     * @param showerTime the time you showered
     * @param description the description
     * @throws ConnectIOException throws an error if you can't connect with the database
     */
    public static void addReduceWater(int showerTime, String description)
            throws ConnectIOException {

        int timeDiff = 20 - showerTime;

        //normal time spent of shower * normal water use during shower (1min)
        // * normal energy contain by water(1KG)
        double carbon = Api.carbonAmount("electricity_uses.json?energy=" + 1.84);
        double carbonReduced = timeDiff * carbon;
        double carbonProduced = showerTime * carbon;

        // if we have solar panels we use a different calculation
        if (hasSolarPanels) {
            ComCached.addAction("ReduceWater", 100 * timeDiff, carbonProduced,
                    0, description);
        } else {
            ComCached.addAction("ReduceWater", 100 * timeDiff, carbonReduced,
                    carbonProduced, description);
        }
    }
}



