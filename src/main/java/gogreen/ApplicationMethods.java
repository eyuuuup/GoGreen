package gogreen;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Scanner;

class ApplicationMethods {
    static void toggleVisibility(TextField visible, PasswordField invisible, boolean show) {
        if (show) {
            invisible.setVisible(false);
            visible.setText(invisible.getText());
            visible.setVisible(true);
        } else {
            invisible.setVisible(true);
            invisible.setText(visible.getText());
            visible.setVisible(false);
        }
    }

    static void login(String username, String password, boolean remember) {
        String encodedUsername = encodeUsername(username);
        String encryptedPassword = encryptPassword(password);
        if (client.Communication.login(encodedUsername, encryptedPassword, remember)) {
            Application.categoryScreen();
        }
    }

    static void register(String username, String password, String passwordTwo, boolean remember)
            throws NullPointerException, IllegalArgumentException {
        checkName(username);

        if (!password.equals(passwordTwo)) {
            throw new IllegalArgumentException("Passwords not equal!");
        }

        if (password.length() <= 7) {
            throw new IllegalArgumentException("Password too short");
        }

        String encodedUsername = encodeUsername(username);
        String encryptedPassword = encryptPassword(password);

        if (client.Communication.register(encodedUsername, encryptedPassword, remember)) {
            Application.categoryScreen();
        } else {
            throw new IllegalArgumentException("Registration unsuccessful");
        }
    }

    private static String encryptPassword(String password) {
        StrongPasswordEncryptor passwordEncrypt = new StrongPasswordEncryptor();
        return passwordEncrypt.encryptPassword(password);
    }

    private static String encodeUsername(String username) {
        String encodedUsername = "";
        try {
            encodedUsername = Base64.getEncoder().encodeToString(
                    username.getBytes("utf-8"));
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        return encodedUsername;
    }

    /**
     * Checks whether a given name is according to the rules.
     *
     * @param testName the name to test
     * @return boolean correct name
     * @throws NullPointerException     if null
     * @throws IllegalArgumentException if invalid
     */
    private static boolean checkName(String testName)
            throws NullPointerException, IllegalArgumentException {
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
        try {
            File file = new File("doc/resources/InvalidNamesComma.txt");
            Scanner sc = new Scanner(file).useDelimiter(", ");
            while (sc.hasNext()) {
                if (testName.contains(sc.next())) {
                    throw new IllegalArgumentException("Offensive name!");
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
                throw new IllegalArgumentException("Invalid character!");
            }
        }
    }
}
