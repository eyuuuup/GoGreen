package goGreen;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private static User USER = new User("Erwin", 1);

    @Test (expected=NullPointerException.class)
    public void createUserNull() {
        User userTwo = new User(null, 1);
        userTwo.changeName(null);
    }

    @Test
    public void createUser() {
        User userTwo = new User("Erwin", 1);
        assertEquals("Erwin", userTwo.getName());
    }

    @Test
    public void getName() {
        assertEquals("Erwin", USER.getName());
    }

    @Test
    public void getNameWrong() {
        assertNotEquals("Jan", USER.getName());
    }

    @Test
    public void changeName() {
        User userTwo = new User("Erwin", 1);
        userTwo.changeName("Jan");
        assertEquals("Jan", userTwo.getName());
    }

    @Test
    public void changeNameWrong() {
        User userTwo = new User("Erwin", 1);
        userTwo.changeName("Jan");
        assertNotEquals("Erwin", userTwo.getName());
    }

    @Test (expected=NullPointerException.class)
    public void changeNameNull() {
        User userTwo = new User("Erwin", 1);
        userTwo.changeName(null);
    }

    @Test
    public void getUserId() {
        assertEquals(1, USER.getUserId());
    }

    @Test
    public void getUserIdWrong() {
        assertNotEquals(2, USER.getUserId());
    }

    @Test
    public void getPoints() {
        assertEquals(0, USER.getPoints());
    }

    @Test
    public void getPointsWrong() {
        assertNotEquals(100, USER.getPoints());
    }

    @Test
    public void addPoints() {
        User userTwo = new User("Erwin", 1);
        userTwo.addPoints(130);
        assertEquals(130, userTwo.getPoints());
    }

    @Test
    public void addMultiplePoints() {
        User userTwo = new User("Erwin", 1);
        userTwo.addPoints(110);
        userTwo.addPoints(140);
        assertEquals(250, userTwo.getPoints());
    }

    @Test
    public void addPointsWrong() {
        User userTwo = new User("Erwin", 1);
        userTwo.addPoints(10);
        assertNotEquals(100, userTwo.getPoints());
    }
}