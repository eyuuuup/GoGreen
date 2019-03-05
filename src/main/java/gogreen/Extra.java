package gogreen;

//Represent of ExtraCategory

public final class Extra{

    //For clean surround place
    public static void addCleansurroundAction() {
        Communication.addAction("CleanSurround", 100);
    }

    //For recycle activity
    public static void addRecycleAction() {
        Communication.addAction("Recycle", 100);
    }
}