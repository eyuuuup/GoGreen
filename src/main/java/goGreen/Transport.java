package goGreen;

public class Transport {
    //for a cycle action you receive 100 points
    public static int addCycleAction(){
        return 100;
    }

    //for a car action you receive 25 points
    public static int addCarAction(){
        return 25;
    }

    //for a plane action you don't get points
    public static int addPlaneAction(){
        return 0;
    }

    //for a public transport action you receive 75 points
    public static int addPublicTransportAction(){
        return 75;
    }
}
