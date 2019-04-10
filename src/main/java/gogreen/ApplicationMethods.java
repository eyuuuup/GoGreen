package gogreen;

import client.Challenge;
import client.Communication;
import client.OnLoadValues;
import com.google.common.hash.Hashing;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

/**
 * This class includes some useful methods for the GUI.
 */
class ApplicationMethods {

    private static int points;
    private static int followingSize;
    private static int followersSize;
    private static double savedCarbon;
    private static String username;
    private static boolean solarPanel;
    private static boolean electricCar;
    private static boolean envGroup;

    private ApplicationMethods() {
    }

    /**
     * To be implemented:
     * checks whether user has solar panels and or an electric car in the database.
     */
    public static void onLoad() {
        // to be implemented: fetch data from the database
        Transport.setHasElectricCar(false);
        Energy.setHasSolarPanels(false);
//        Communication.onLoad(); Returns an object of class onLoadValues but action ids aren't set properly on server
    }

    /**
     * This methods logs in using the given username and password.
     *
     * @param username the username
     * @param password the password
     * @param remember whether to remember this user
     */
    static void login(String username, String password, boolean remember)
            throws IllegalAccessException {
        String encodedUsername = encodeUsername(username);
        String hashedPassword = hashPassword(password);

        if (client.Communication.login(encodedUsername, hashedPassword, remember)) {

            setPresets();
            Application.mainScreen();
        } else {
            throw new IllegalAccessException("Login unsuccessful");
        }
    }

    /**
     * This methods registers using the given username and password.
     *
     * @param username    the username
     * @param password    the password
     * @param passwordTwo the rewritten password
     * @param remember    whether to remember this user
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
        String hashedPassword = hashPassword(password);

        if (client.Communication.register(encodedUsername, hashedPassword, remember)) {
            Application.mainScreen();
        } else {
            throw new IllegalAccessException("Registration unsuccessful");
        }
    }

    /**
     * This method hashes the given password, using SHA256.
     *
     * @param password the password
     * @return the hashed password
     */
    private static String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    /**
     * This method encodes the username.
     *
     * @param username the username
     * @return the encoded username
     */
    public static String encodeUsername(String username) {
        return Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * This method decodes the username.
     *
     * @param username the username
     * @return the decoded username
     */
    public static String decodeUsername(String username) {
        return new String(Base64.getDecoder().decode(username));

    }

    /**
     * Checks whether a given name is according to the rules.
     *
     * @param testName the name to test
     * @throws NullPointerException     if null
     * @throws IllegalArgumentException if invalid
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
        File file = new File("src/extraFiles/InvalidNamesComma.txt");
        Scanner sc = new Scanner(file).useDelimiter(", ");
        while (sc.hasNext()) {
            if (testName.contains(sc.next())) {
                throw new IllegalArgumentException("Offensive name!");
            }
        }
        sc.close();
    }

    /**
     * checks the characters in the new name.
     *
     * @param testName the new name
     */
    private static void checkCharacters(String testName) {
        for (char c : testName.toCharArray()) {
            if (!(Character.toString(c).toLowerCase()).matches("[a-zA-Z]")) {
                throw new IllegalArgumentException("Invalid character!");
            }
        }
    }

    static int getLevel(int points) {
        return (int) (Math.floor((-1 + Math.sqrt(1 + 8 * (points / 50 + 10))) / 2) - 3);
    }

    static int getLevelInv(int lvl) {
        lvl = (int) Math.floor(lvl) - 1;
        return 50 * lvl * (9 + lvl) / 2;
    }

    static double getLevelProgress(int points) {
        int lvl = getLevel(points);
        double start = getLevelInv(lvl);
        double end = getLevelInv(lvl + 1);
        return (points - start) / (end - start);
    }

    static void setPresets() {
        Application.loadingScreen();
        try {
            OnLoadValues onload = Communication.onLoad();
            points = onload.getUser().getTotalScore();
            followingSize = onload.getFollowing();
            followersSize = onload.getFollowers();
            username = onload.getUser().getName();
            solarPanel = onload.isSolarPanel();
            electricCar = onload.isElectricCar();
            envGroup = onload.isEnvGroup();
            // search here shruti
            savedCarbon = onload.getCarbonReduce();
            savedCarbon = savedCarbon * 100;
            savedCarbon = (int) savedCarbon;
            savedCarbon = savedCarbon / 100;
        } catch (NullPointerException e) {
            points = 0;
            followingSize = 0;
            followersSize = 0;
            savedCarbon = 0;
        }
    }

    static int getPoints() {
        return points;
    }

    static int getFollowingSize() {
        return followingSize;
    }

    static int getFollowersSize() {
        return followersSize;
    }

    static String getUsername() {
        return username;
    }

    static double getSavedCarbon() {
        return savedCarbon;
    }

    public static boolean isSolarPanel() {
        return solarPanel;
    }

    public static boolean isElectricCar() {
        return electricCar;
    }

    public static boolean isEnvGroup() {
        return envGroup;
    }
}
