package client;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChallengesListTest {

    @Test
    public void constructor() {
        ArrayList<Challenge> listA = new ArrayList<>();
        ArrayList<Challenge> listB = new ArrayList<>();
        ChallengesList challenges = new ChallengesList(listA, listB);

        assertEquals(challenges.getList(), listA);
        assertEquals(challenges.getReceivedList(), listB);
    }

    @Test
    public void emptyConstructor() {
        ChallengesList list = new ChallengesList();
        assertNotNull(list);
        assertNotNull(list.getList());
        assertNotNull(list.getReceivedList());
    }

    @Test
    public void setReceivedList() {
        ChallengesList list = new ChallengesList();
        ArrayList<Challenge> challenge = new ArrayList<>();
        list.setReceivedList(challenge);
        assertEquals(list.getReceivedList(), challenge);
    }

    @Test
    public void getReceivedList() {
        ArrayList<Challenge> listA = new ArrayList<>();
        ArrayList<Challenge> listB = new ArrayList<>();
        ChallengesList challenges = new ChallengesList(listA, listB);

        assertEquals(challenges.getReceivedList(), listB);
    }

    @Test
    public void getList() {
        ArrayList<Challenge> listA = new ArrayList<>();
        ArrayList<Challenge> listB = new ArrayList<>();
        ChallengesList challenges = new ChallengesList(listA, listB);

        assertEquals(challenges.getList(), listA);
    }

    @Test
    public void setList() {
        ChallengesList list = new ChallengesList();
        ArrayList<Challenge> challenge = new ArrayList<>();
        list.setList(challenge);
        assertEquals(list.getList(), challenge);
    }
}