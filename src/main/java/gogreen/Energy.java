package gogreen;

//Represent the Energy Category

import client.Communication;

public final class Energy {

    //Method for reduce energy use
    public static void addReduceEnergyAction() {

        Communication.addRequest("ReduceEnergy", 100);
    }

    //Method for less showertime
    public static void addReduceWater() {
        Communication.addRequest("ReduceWater", 100);
    }

    //Method for turn off electricity
    public static void addReduceElectricity() {
        Communication.addRequest("ReduceElectric", 100);
    }
}



