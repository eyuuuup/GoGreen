package gogreen;

//Represent the Energy Category

public final class Energy {

    //Method for reduce energy use
    public static void addReduceEnergyAction() {

        Communication.addAction("ReduceEnergy", 100);
    }

    //Method for less showertime
    public static void addReduceWater() {
        Communication.addAction("ReduceWater", 100);
    }

    //Method for turn off electricity
    public static void addReduceElectricity() {
        Communication.addAction("ReduceElectric", 100);
    }
}



