package client;

import java.util.ArrayList;

/**
 * This class is for the list of challenges
 */
public class ChallengesList {

    ArrayList<Challenge> list;

    public ChallengesList() {
        list = new ArrayList<Challenge>();
    }

    public ChallengesList(ArrayList<Challenge> list) {
        this.list = list;
    }

    public ArrayList<Challenge> getList() {
        return list;
    }

    public void setList(ArrayList<Challenge> list) {
        this.list = list;
    }

}
