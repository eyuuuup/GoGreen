package client;

import java.util.ArrayList;

/**
 * This class represents a list of challenges.
 */
public class ChallengesList {

    ArrayList<Challenge> list;

    /**
     * Constructor for ChallengeList.
     */
    public ChallengesList() {
        list = new ArrayList<Challenge>();
    }

    /**
     * Constructor for ChallengeList.
     * @param list list of Challenges
     */
    public ChallengesList(ArrayList<Challenge> list) {
        this.list = list;
    }

    /**
     * Getter for the list of challenges.
     * @return the list of challenges
     */
    public ArrayList<Challenge> getList() {
        return list;
    }

    /**
     * Setter for the list of challenges.
     * @param list the list of challenges
     */
    public void setList(ArrayList<Challenge> list) {
        this.list = list;
    }
}
