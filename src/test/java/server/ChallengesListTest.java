package server;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

    @Test
    public void equalsObj(){
        String s="hello";
        ChallengesList list=new ChallengesList();
        assertFalse(list.equals(s));

        Challenge challenge=new Challenge("a", "b", 40,40,3);
        ArrayList<Challenge> test=new ArrayList<>();
        test.add(challenge);
        ChallengesList testList = new ChallengesList(test);
        assertFalse(list.equals(testList));

        ChallengesList testEqual= new ChallengesList(test);
        assertTrue(testEqual.equals(testList));
    }
}
