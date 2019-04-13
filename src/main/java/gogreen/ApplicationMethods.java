package gogreen;

import com.google.common.hash.Hashing;

import gogreen.actions.Energy;
import gogreen.actions.Transport;
import gogreen.server.ComCached;
import gogreen.server.holders.OnLoadValues;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

/**
 * This class includes some useful methods for the GUI.
 */
class ApplicationMethods {

    private static int     points;
    private static int     followingSize;
    private static int     followersSize;
    private static double  savedCarbon;
    private static String  username;
    private static boolean solarPanel;
    private static boolean electricCar;
    private static boolean envGroup;

    /**
     * private empty constructor.
     */
    private ApplicationMethods() {
    }

    /**
     * checks whether user has solar panels and or an electric car in the server.database.
     */
    public static void onLoad() {
        // to be implemented: fetch data from the server.database
        Transport.setHasElectricCar(false);
        Energy.setHasSolarPanels(false);
        // Returns an object of class onLoadValues but action ids aren't set properly on server
        // ComCached.onLoad();

    }

    /**
     * This methods logs in using the given username and password.
     * @param username the username
     * @param password the password
     * @param remember if the user should be remembered
     * @throws IllegalAccessException if the combination is incorrect we throw an error
     */
    static void login(String username, String password, boolean remember)
            throws IllegalAccessException {
        String encodedUsername = encodeUsername(username);
        String hashedPassword  = hashPassword(password);

        // tries to login
        if (gogreen.server.ComCached.login(encodedUsername, hashedPassword, remember)) {
            setPresets();
            Application.mainScreen();
        } else {
            throw new IllegalAccessException("User not found");
        }
    }

    /**
     * This methods registers using the given username and password.
     * @param username the username
     * @param password the password
     * @param passwordTwo the second password
     * @param remember if we wanted to be remembered
     * @throws NullPointerException when we get an null pointer we throw an error
     * @throws IllegalArgumentException if one of the inputs is not correct we throw an error
     * @throws IllegalAccessException if the username and password combination is incorrect
     *                                we throw an error
     * @throws FileNotFoundException if the token is not found we throw an error
     */
    static void register(String username, String password, String passwordTwo, boolean remember)
            throws NullPointerException, IllegalArgumentException,
            IllegalAccessException, FileNotFoundException {
        checkName(username);

        if (!password.equals(passwordTwo)) {
            throw new IllegalArgumentException("Passwords not equal!");
        }

        if (password.length() <= 2) {
            throw new IllegalArgumentException("Password too short");
        }

        String encodedUsername = encodeUsername(username);
        String hashedPassword  = hashPassword(password);

        if (gogreen.server.ComCached.register(encodedUsername, hashedPassword, remember)) {
            setPresets();
            Application.mainScreen();
        } else {
            throw new IllegalAccessException("Username was already taken");
        }
    }

    /**
     * This method hashes the given password, using SHA256.
     * @param password the password
     * @return the hashed password
     */
    private static String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    /**
     * This method encodes the username.
     * @param username the username
     * @return the encoded username
     */
    public static String encodeUsername(String username) {
        return Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * This method decodes the username.
     * @param username the username
     * @return the decoded username
     */
    public static String decodeUsername(String username) {
        return new String(Base64.getDecoder().decode(username));

    }

    /**
     * Checks whether a given name is according to the rules.
     * @param testName the name to test
     * @throws NullPointerException if we get a null pointer we throw an error
     * @throws IllegalArgumentException if we give an illegal input we throw an error
     */
    private static void checkName(String testName)
            throws NullPointerException, IllegalArgumentException, FileNotFoundException {
        //check if name is null
        if (testName == null) {
            throw new NullPointerException("Name equals null!");
        }

        //check whether name is too long
        if (testName.length() >= 16) {
            throw new IllegalArgumentException("Name is too long!");
        }

        //check if name is too short
        if (testName.length() <= 0) {
            throw new IllegalArgumentException("Name is too short!");
        }

        //check if all characters are permitted
        checkCharacters(testName);

        //for the invalid name test the name should consist of lowercase letters
        testName = testName.toLowerCase();

        //check whether the name is not offensive
        File    file = new File("src/main/resources/extraFiles/InvalidNamesComma.txt");
        Scanner sc   = new Scanner(file).useDelimiter(", ");
        while (sc.hasNext()) {
            if (testName.contains(sc.next())) {
                throw new IllegalArgumentException("Offensive name!");
            }
        }
        sc.close();
    }

    /**
     * checks the characters in the new name.
     * @param testName the new name
     */
    private static void checkCharacters(String testName) {
        for (char c : testName.toCharArray()) {
            if (!(Character.toString(c).toLowerCase()).matches("[a-zA-Z]")) {
                throw new IllegalArgumentException("Invalid character!");
            }
        }
    }

    /**
     * we calculate the level out of the points.
     * @param points the points
     * @return the level
     */
    static int getLevel(int points) {
        return (int) (Math.floor((-1 + Math.sqrt(1 + 8 * (points / 50 + 10))) / 2) - 3);
    }

    /**
     * we calculate the points out the level.
     * @param lvl the level
     * @return the points
     */
    static int getLevelInv(int lvl) {
        lvl = (int) Math.floor(lvl) - 1;
        return 50 * lvl * (9 + lvl) / 2;
    }

    /**
     * we get the progress till the next level.
     * @param points the points
     * @return the progress till the next level
     */
    static double getLevelProgress(int points) {
        int    lvl   = getLevel(points);
        double start = getLevelInv(lvl);
        double end   = getLevelInv(lvl + 1);
        return (points - start) / (end - start);
    }

    /**
     * sets all the presets.
     */
    static void setPresets() {
        System.out.println("Loading..");
        try {
            OnLoadValues onload = ComCached.onLoad();
            points = onload.getUser().getTotalScore();
            followingSize = onload.getFollowing();
            followersSize = onload.getFollowers();
            username = onload.getUser().getName();
            solarPanel = onload.isSolarPanel();
            electricCar = onload.isElectricCar();
            envGroup = onload.isEnvGroup();
            savedCarbon = onload.getCarbonReduce();
            savedCarbon = savedCarbon * 100;
            savedCarbon = (int) savedCarbon;
            savedCarbon = savedCarbon / 100;

            Transport.setHasElectricCar(onload.isElectricCar());
            Energy.setHasSolarPanels(onload.isSolarPanel());
        } catch (NullPointerException e) {
            points = 0;
            followingSize = 0;
            followersSize = 0;
            savedCarbon = 0;
        }
    }

    /**
     * get the progress of the challenge.
     * @param start the points you start with
     * @param goal the goal
     * @return the progress of the challenge
     */
    static double getChallengeProgress(int start, int goal) {
        if (goal == 0) {
            throw new ArithmeticException("cannot divide by 0");
        }
        Double value = ((double) (ApplicationMethods.getPoints() - start)) / (double) goal;
        System.out.println(value);
        if (value < 0) {
            return 0;
        }
        return value;
    }

    /**
     * returns the points.
     * @return the points
     */
    static int getPoints() {
        return points;
    }

    /**
     * returns the amount of people you follow.
     * @return the amount of people you follow
     */
    static int getFollowingSize() {
        return followingSize;
    }

    /**
     * returns the amount of people who follow you.
     * @return the amount of people who follow you
     */
    static int getFollowersSize() {
        return followersSize;
    }

    /**
     * returns the username.
     * @return the username
     */
    static String getUsername() {
        return username;
    }

    /**
     * returns the amount of co2 saved.
     * @return the amount of co2 saved
     */
    static double getSavedCarbon() {
        return savedCarbon;
    }

    /**
     * returns if we have solar panels.
     * @return is we have solar panels
     */
    public static boolean isSolarPanel() {
        return solarPanel;
    }

    /**
     * returns if we have an electric car.
     * @return if we have an electric car
     */
    public static boolean isElectricCar() {
        return electricCar;
    }

    /**
     * returns if we joined an environment group.
     * @return if we joined an environment group
     */
    public static boolean isEnvGroup() {
        return envGroup;
    }

}
