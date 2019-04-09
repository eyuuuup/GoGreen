package server;

import java.util.ArrayList;

/**
 * This class is for the list of challenges
 */
public class ChallengesList {

    ArrayList<Challenge> list;
    ArrayList<Challenge> receivedList;

    public ChallengesList() {
        list = new ArrayList<Challenge>();
        receivedList = new ArrayList<Challenge>();
    }

    public ChallengesList(ArrayList<Challenge> list, ArrayList<Challenge> receivedList) {
        this.list = list;
        this.receivedList = receivedList;
    }

    public void setReceivedList(ArrayList<Challenge> receivedList) {
        this.receivedList = receivedList;
    }

    public ArrayList<Challenge> getReceivedList() {
        return receivedList;
    }

    public ArrayList<Challenge> getList() {
        return list;
    }

    public void setList(ArrayList<Challenge> list) {
        this.list = list;
    }

}
