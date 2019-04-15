package server;

import org.junit.Test;
import server.holders.Challenge;
import server.holders.ChallengesList;
import server.holders.OnLoadValues;
import server.holders.User;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OnLoadValuesTest {

    private User                 user  = new User("user","password", "mail", 150);
    private ArrayList<Challenge> listA = new ArrayList<>();
    private ChallengesList       list  =new ChallengesList(listA);
    private OnLoadValues         test  = new OnLoadValues(user, 2,3,list,false,false, false,50);

    @Test
    public void emptyConstructor(){
        OnLoadValues o=new OnLoadValues();
        assertNotNull(o.getChallenges());
        assertNotNull(o.getCarbonReduce());
        assertNotNull(o.getFollowers());
        assertNotNull(o.getFollowing());
        assertNotNull(o.getUser());
        assertFalse(o.isEnvGroup());
        assertFalse(o.isSolarPanel());
        assertFalse(o.isElectricCar());
    }

    @Test
    public void getUser() {
        assertEquals(user, test.getUser());
    }

    @Test
    public void getFollowers() {
        assertEquals(2,test.getFollowers());
    }

    @Test
    public void getFollowing() {
        assertEquals(3, test.getFollowing());
    }

    @Test
    public void getChallenges() {
        assertEquals(list, test.getChallenges());
    }

    @Test
    public void setUser() {
        User user2=new User("aa","ss", "ss", 5);
        test.setUser(user2);
        assertEquals(user2,test.getUser());
    }

    @Test
    public void setFollowers() {
        test.setFollowers(3);
        assertEquals(3,test.getFollowers());
    }

    @Test
    public void setFollowing() {
        test.setFollowing(5);
        assertEquals(5,test.getFollowing());
    }

    @Test
    public void setChallenges() {
        ChallengesList list2 = new ChallengesList(new ArrayList<Challenge>());
        test.setChallenges(list2);
        assertEquals(list2,test.getChallenges());
    }

    @Test
    public void isSolarPanel() {
        assertFalse(test.isSolarPanel());
    }

    @Test
    public void setSolarPanel() {
        test.setSolarPanel(true);
        assertTrue(test.isSolarPanel());
    }

    @Test
    public void isElectricCar() {
        assertFalse(test.isElectricCar());
    }

    @Test
    public void setElectricCar() {
        test.setElectricCar(true);
        assertTrue(test.isElectricCar());
    }

    @Test
    public void isEnvGroup() {
        assertFalse(test.isEnvGroup());
    }

    @Test
    public void setEnvGroup() {
        test.setEnvGroup(true);
        assertTrue(test.isEnvGroup());
    }

    @Test
    public void getCarbonReduce() {
        assertEquals(50, test.getCarbonReduce(), 0);
    }

    @Test
    public void setCarbonReduce() {
        test.setCarbonReduce(150);
        assertEquals(150, test.getCarbonReduce(), 0);
    }
}