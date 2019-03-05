package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * this class represents an User of the gogreen Application.
 *
 * @author Erwin van Dam
 */
public class User {
    /**
     * The User name.
     */
    private String name;
    /**
     * The User Id.
     */
    private int    userId;
    /**
     * The User's points.
     */
    private int    points;

    /**
     * This method creates and instantiates a new User.
     *
     * @param newName   the Name
     * @param newUserId the User Id
     */
    public User(final String newName, final int newUserId) {
        if (checkName(newName)) {
            this.name = newName;
            this.userId = newUserId;
            points = 0;
        }
    }

    /**
     * Checks whether a given name is according to the rules.
     *
     * @param testName the name to test
     * @return boolean correct name
     * @throws NullPointerException     if null
     * @throws IllegalArgumentException if invalid
     */
    public static boolean checkName(final String testName)
            throws NullPointerException, IllegalArgumentException {
        if (testName == null) {
            throw new NullPointerException("Name equals null");
        }

        final int maxSize = 16;
        if (testName.length() >= maxSize) {
            throw new IllegalArgumentException("Name is too long");
        }
        if (testName.length() <= 0) {
            throw new IllegalArgumentException("Name is too short");
        }

        checkCharacters(testName);

        //check whether the name is not offensive
        try {
            File    file = new File("doc/resources/InvalidNamesComma.txt");
            Scanner sc   = new Scanner(file).useDelimiter(", ");
            while (sc.hasNext()) {
                if (testName.equals(sc.next())) {
                    throw new IllegalArgumentException("Offensive name");
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * checks the characters in the new name.
     *
     * @param testName the new name
     */
    private static void checkCharacters(String testName) {
        //check if all characters in the name are valid characters
        for (char c : testName.toCharArray()) {
            if (!(Character.toString(c).toLowerCase()).matches("[a-zA-Z]")) {
                throw new IllegalArgumentException("Invalid character");
            }
        }
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes name into newName.
     *
     * @param newName the new name
     */
    public void changeName(final String newName) {
        if (checkName(newName)) {
            name = newName;
        }
    }

    /**
     * Getter for userId.
     *
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for points.
     *
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * adds extra points to points.
     *
     * @param extraPoints points to add
     */
    public void addPoints(final int extraPoints) {
        //don't know what restrictions to apply here, anyone?
        points += extraPoints;
    }
}
