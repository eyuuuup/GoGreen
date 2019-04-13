package server;

import java.util.ArrayList;
import java.util.Objects;

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

    /**
     * Equals method for ChallengeList.
     * @param other the other object.
     * @return whether the other object equals this.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChallengesList)) {
            return false;
        }
        ChallengesList that = (ChallengesList) other;
        return Objects.equals(list, that.list);
    }

}
