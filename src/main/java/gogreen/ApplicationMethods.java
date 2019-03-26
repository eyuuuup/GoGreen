package gogreen;

import com.google.common.hash.Hashing;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

/**
 * This class includes some useful methods for the GUI.
 */
class ApplicationMethods {
    private ApplicationMethods() {
    }
    
    /**
     * This methods logs in using the given username and password.
     * @param username the username
     * @param password the password
     * @param remember whether to remember this user
     */
    static void login(String username, String password, boolean remember)
            throws IllegalAccessException {
        String encodedUsername = encodeUsername(username);
        String hashedPassword  = hashPassword(password);
        
        if (client.Communication.login(encodedUsername, hashedPassword, remember)) {
            Application.mainScreen();
        } else {
            throw new IllegalAccessException("Login unsuccessful");
        }
    }
    
    /**
     * This methods registers using the given username and password.
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
        String hashedPassword  = hashPassword(password);
        
        if (client.Communication.register(encodedUsername, hashedPassword, remember)) {
            Application.mainScreen();
        } else {
            throw new IllegalAccessException("Registration unsuccessful");
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
        File    file = new File("src/extraFiles/InvalidNamesComma.txt");
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
    
    //    /**
    //     * makes a string of the recent activities.
    //     * @param recentActivities recent activities
    //     * @return a String of the recent activities
    //     */
    //    static String recentActivitiesToString(String recentActivities) {
    //                if (recentActivities != null) {
    //                    System.out.println("rA: " + recentActivities);
    //                    String[] split = recentActivities.split("\\s|_");
    //                    String output = split[0] + "\t\t\t\t" +
    //                    split[1] + "\t\t" + split[2] + "\n"
    //                            + split[3] + "\t\t\t\t" + split[4] + "\t\t" + split[5] + "\n"
    //                            + split[6] + "\t\t\t\t" + split[7] + "\t\t" + split[8];
    //                    System.out.println(output);
    //                    return output;
    //                }
    //        return "none";
    //    }
    
    static int getLevel(int points) {
        return (int) (Math.floor((-1 + Math.sqrt(1 + 8 * (points / 50 + 10))) / 2) - 3);
    }
    
    static int getLevelInv(int lvl) {
        lvl = (int) Math.floor(lvl) - 1;
        return 50 * lvl * (9 + lvl) / 2;
    }
    
    static double getLevelProgress(int points) {
        int    lvl   = getLevel(points);
        double start = getLevelInv(lvl);
        double end   = getLevelInv(lvl + 1);
        return (points - start) / (end - start);
    }
}
