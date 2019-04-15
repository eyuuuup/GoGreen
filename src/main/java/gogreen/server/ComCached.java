package gogreen.server;

import gogreen.server.holders.Action;
import gogreen.server.holders.CompareFriends;
import gogreen.server.holders.User;

import java.util.ArrayList;

/**
 * This class represents the caching of the most frequently used variables in the application.
 */
public class ComCached extends Communication {

    private static User                      userChanged    = null;
    private static Integer                   totalChanged   = null;
    private static ArrayList<Action>         lastChanged    = null;
    private static double[]                  coChanged      = null;
    private static Action                    carbonChanged  = null;
    private static ArrayList<CompareFriends> friendsChanged = null;

    /**
     * Removes the cached user on a register event.
     * @param username the user's username
     * @param password the user's password
     * @param remember whether to remember the user
     * @return whether the registration was successful
     */
    public static boolean register(String username, String password, boolean remember) {
        userChanged = null;
        return Communication.register(username, password, remember);
    }

    /**
     * Removes the cached user on a login event.
     * @param username the user's username
     * @param password the user's password
     * @param remember whether to remember the user
     * @return whether the login was successful
     */
    public static boolean login(String username, String password, boolean remember) {
        userChanged = null;
        return Communication.login(username, password, remember);
    }

    /**
     * Removes the cached user on a silentLogin event.
     * @return whether the silentLogin was successful
     */
    public static boolean silentLogin() {
        userChanged = null;
        return Communication.silentLogin();
    }

    /**
     * Removes the cached user, the user's total score, the user's last three actions,
     * the user's recent CO2 reductions, the user's total CO2 reduction and the user's
     * list of friends, and logs out.
     */
    public static void logout() {
        userChanged = null;
        totalChanged = null;
        lastChanged = null;
        coChanged = null;
        carbonChanged = null;
        friendsChanged = null;
        Communication.logout();
    }

    /**
     * Adds an Action with an action name, the amount of points for the action,
     * the amount of CO2 reduced by this action and the amount of CO2 produced by this action.
     * @param actionName     the action name
     * @param points         the amount of points for the action
     * @param carbonReduced  the amount of CO2 reduced by the action
     * @param carbonProduced the amount of CO2 produced by this action
     * @return whether the action was successfully committed
     */
    public static boolean addAction(String actionName, int points, double carbonReduced,
                                    double carbonProduced) {
        return ComCached.addAction(actionName, points, carbonReduced, carbonProduced, null);
    }

    /**
     * Adds an Action with a action name, the amount of points for the action,
     * the amount of CO2 reduced by this action, the amount of CO2 produced by this action
     * and the user's description about the action. This method also removes the user's cached
     * total score, recent CO2 reductions, total reduction and last three actions.
     * @param actionName     the action name
     * @param points         the amount of points for the action
     * @param carbonReduced  the amount of CO2 reduced by the action
     * @param carbonProduced the amount of CO2 produced by this action
     * @param description    the user's description about the action
     * @return whether the action was successfully committed
     */
    public static boolean addAction(String actionName, int points, double carbonReduced,
                                    double carbonProduced, String description) {
        totalChanged = null;
        coChanged = null;
        carbonChanged = null;
        lastChanged = null;
        return Communication.addAction(actionName, points, carbonReduced, carbonProduced,
                description);
    }

    /**
     * Getter for the user's last three actions, uses the cached last three actions when possible.
     * @return the user's three last actions
     */
    public static ArrayList<Action> getLastThreeActions() {
        if (lastChanged == null) {
            lastChanged = Communication.getLastThreeActions();
        }
        return lastChanged;
    }

    /**
     * Getter for the user's total score, uses the cached total score if possible.
     * @return the user's total score
     */
    public static int getMyTotalScore() {
        if (totalChanged == null) {
            totalChanged = Communication.getMyTotalScore();
        }
        return totalChanged;
    }

    /**
     * Getter for the user, uses the cached user when possible.
     * @return the user
     */
    public static User getUser() {
        if (userChanged == null) {
            userChanged = Communication.getUser();
        }
        return userChanged;
    }

    /**
     * Removes the cached user's friends and adds a friend to the user's friends.
     * @param friendUsername the username of the friend to add
     * @return whether the friend was added successfully
     */
    public static boolean addFriend(String friendUsername) {
        friendsChanged = null;
        return Communication.addFriend(friendUsername);
    }

    /**
     * Getter for the user's list of friends, uses the cached list of friends if possible.
     * @return the user's list of friends
     */
    public static ArrayList<CompareFriends> getFriends() {
        if (friendsChanged == null) {
            friendsChanged = Communication.getFriends();
        }
        return friendsChanged;
    }

    /**
     * Getter for the user's total CO2 reduction, uses the cached CO2 reduction if possible.
     * @return the user's total CO2 reduction
     */
    public static Action carbon() {
        if (carbonChanged == null) {
            carbonChanged = Communication.carbon();
        }
        return carbonChanged;
    }

    /**
     * Getter for the user's recent CO2 reductions, uses the cached CO2 reductions if possible.
     * @return the user's recent CO2 reductions
     */
    public static double[] getRecentCoSavings() {
        if (coChanged == null) {
            coChanged = Communication.getRecentCoSavings();
        }
        return coChanged;
    }


    // ==========================  SETTERS =====================================

    /**
     * Caches the user.
     * @param userChanged the user
     */
    public static void setUserChanged(User userChanged) {
        ComCached.userChanged = userChanged;
    }

    /**
     * Caches the user's total score.
     * @param totalChanged the user's total score
     */
    public static void setTotalChanged(Integer totalChanged) {
        ComCached.totalChanged = totalChanged;
    }

    /**
     * Caches the user's last three actions.
     * @param lastChanged the user's last three actions
     */
    public static void setLastChanged(ArrayList<Action> lastChanged) {
        ComCached.lastChanged = lastChanged;
    }

    /**
     * Caches the user's recent CO2 reductions.
     * @param coChanged the user's recent CO2 reductions
     */
    public static void setCoChanged(double[] coChanged) {
        ComCached.coChanged = coChanged;
    }

    /**
     * Caches the user's total amount of CO2 reduction.
     * @param carbonChanged the user's total amount of CO2 reduction
     */
    public static void setCarbonChanged(Action carbonChanged) {
        ComCached.carbonChanged = carbonChanged;
    }

    /**
     * Caches the user's list of friends.
     * @param friendsChanged the user's list of friends.
     */
    public static void setFriendsChanged(ArrayList<CompareFriends> friendsChanged) {
        ComCached.friendsChanged = friendsChanged;
    }
}
