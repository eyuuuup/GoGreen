package server;

/**
 * Class to be replaced.
 */
public class ReplaceByDatabaseMethods {
    /**
     *checks if username and password right or not.
     * @param userDetails (username, password)
     * @return TokenResponse (token, boolean)
     */
    public static TokenResponse checkLogin(User userDetails) {
        String token = null;
        boolean bool = false;
        return new TokenResponse(token, bool);
    }

    /**
     * checks if username already exists or not in database.
     * @param username string
     * @return
     */
    public static boolean checkUsername(String username) {
        //if exists return false
        boolean bool = false;
        return  bool;
    }

    /**
     * adds new user in the database.
     * @param userDeatails string
     */
    public static void setNewUser(User userDeatails, String token) {

    }

    /**
     * add actoin to database and add points.
     * @param action token, action, points
     * @return
     */
    public static boolean addAction(Action action) {
        //if action added return true
        return true;
    }
}
