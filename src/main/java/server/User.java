package server;

/**
 * this class represents an User of the gogreen Application.
 *
 * @author Erwin van Dam
 */
public class User {
    private static String name;
    private static String password;

    /**
     * This method creates and instantiates a new User.
     *
     * @param name  the Name
     * @param password the password
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    //    /**
    //     * Changes name into newName.
    //     *
    //     * @param newName the new name
    //     */
    //    public void changeName(String newName) {
    //        name = newName;
    //    }

    /**
     * Getter for password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    //    /**
    //     * Changes password into new password.
    //     *
    //     * @param newPassword the new password
    //     */
    //    public void changePassword(String newPassword) {
    //        this.password = newPassword;
    //    }
}
