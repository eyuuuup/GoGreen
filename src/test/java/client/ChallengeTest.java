package client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChallengeTest {

    private Challenge test= new Challenge("a", "b", 10, 20,30);

    @Test
    public void emptyConstructor()
    {
        Challenge test= new Challenge();
        test.setUserA("aa");
        test.setScoreB(30);
        assertEquals("aa", test.getUserA());
        assertEquals(30 , test.getScoreB());
    }

    @Test
    public void constructor()
    {
        Challenge test= new Challenge("a", "b", 10, 20,30);
        assertEquals("a",test.getUserA());
        assertEquals("b", test.getUserB());
        assertEquals(10, test.getScoreA());
        assertEquals(20, test.getScoreB());
        assertEquals(30,test.getGoal());
    }

    @Test
    public void getUserA() {
        assertEquals("a", test.getUserA());
    }

    @Test
    public void setUserA() {
        test.setUserA("aa");
        assertEquals("aa", test.getUserA());
    }

    @Test
    public void getUserB() {
        assertEquals("b", test.getUserB());
    }

    @Test
    public void setUserB() {
        test.setUserB("bb");
        assertEquals("bb", test.getUserB());
    }

    @Test
    public void getScoreA() {
        assertEquals(10,test.getScoreA());
    }

    @Test
    public void setScoreA() {
        test.setScoreA(30);
        assertEquals(30,test.getScoreA());
    }

    @Test
    public void getScoreB() {
        assertEquals(20, test.getScoreB());
    }

    @Test
    public void setScoreB() {
        test.setScoreB(30);
        assertEquals(30, test.getScoreB());
    }

    @Test
    public void getGoal() {
        assertEquals(test.getGoal(),30);
    }

    @Test
    public void setGoal() {
        test.setGoal(40);
        assertEquals(40, test.getGoal());
    }

    @Test
    public void getState(){
        test.setState(1);
        assertEquals(1, test.getState());
    }

    @Test
    public void setState(){
        test.setState(0);
        assertEquals(0,test.getState());
    }

    @Test
    public void setOnA(){
        test.setOnA(false);
        assertEquals(false, test.isOnA());
    }

    @Test
    public void isOnA(){
        assertEquals(false,test.isOnA());
    }
}