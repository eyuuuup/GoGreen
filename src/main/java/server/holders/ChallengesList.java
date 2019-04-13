package server.holders;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChallengesList)) return false;
        ChallengesList that = (ChallengesList) o;
        return Objects.equals(list, that.list);
    }

}
