package gogreen;

import client.Communication;

/**
 * Represent the Energy Category.
 * @author Gyum cho
 */
final class Energy {
    private Energy() {}

    /**
     * Method for reduce energy use.
     */
    static void addReduceEnergyAction() {
        Communication.addAction("ReduceEnergy", 100);
    }

    /**
     * Method for less shower time.
     */
    static void addReduceWater() {
        Communication.addAction("ReduceWater", 100);
    }

    /**
     * Method for reduction electricity usage.
     */
    static void addReduceElectricity() {
        Communication.addAction("ReduceElectricity", 100);
    }
}



