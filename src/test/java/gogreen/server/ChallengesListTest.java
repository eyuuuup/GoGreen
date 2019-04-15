package gogreen.server;

import gogreen.server.holders.Challenge;
import gogreen.server.holders.ChallengesList;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChallengesListTest {

    @Test
    public void constructor() {
        ArrayList<Challenge> listA      = new ArrayList<>();
        ChallengesList       challenges = new ChallengesList(listA);

        assertEquals(challenges.getList(), listA);
    }

    @Test
    public void emptyConstructor() {
        ChallengesList list = new ChallengesList();
        assertNotNull(list);
        assertNotNull(list.getList());
    }

    @Test
    public void getList() {
        ArrayList<Challenge> listA      = new ArrayList<>();
        ChallengesList       challenges = new ChallengesList(listA);

        assertEquals(challenges.getList(), listA);
    }

    @Test
    public void setList() {
        ChallengesList       list      = new ChallengesList();
        ArrayList<Challenge> challenge = new ArrayList<>();
        list.setList(challenge);
        assertEquals(list.getList(), challenge);
    }
}