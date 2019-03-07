package gogreen;

//Represent of ExtraCategory

import client.Communication;

public final class Extra{

    //For clean surround place
    public static void addCleansurroundAction() {
        Communication.addRequest("CleanSurround", 100);
    }

    //For recycle activity
    public static void addRecycleAction() {
        Communication.addRequest("Recycle", 100);
    }
}