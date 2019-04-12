package client;

import java.util.ArrayList;

public class ComCached extends Communication {

    private static User                      userChanged    = null;
    private static Integer                   totalChanged   = null;
    private static ArrayList<Action>         lastChanged    = null;
    private static double[]                  coChanged      = null;
    private static Action                    carbonChanged  = null;
    private static ArrayList<CompareFriends> friendsChanged = null;

    public static boolean register(String username, String password, boolean remember) {
        userChanged = null;
        return Communication.register(username, password, remember);
    }

    public static boolean login(String username, String password, boolean remember) {
        userChanged = null;
        return Communication.login(username, password, remember);
    }

    public static boolean silentLogin() {
        userChanged = null;
        return Communication.silentLogin();
    }

    public static void logout() {
        userChanged = null;
        totalChanged = null;
        lastChanged = null;
        coChanged = null;
        carbonChanged = null;
        friendsChanged = null;
        Communication.logout();
    }

    public static boolean addAction(String actionName, int points, double carbonReduced, double carbonProduced) {
        return ComCached.addAction(actionName, points, carbonReduced, carbonProduced, null);
    }

    public static boolean addAction(String actionName, int points, double carbonReduced, double carbonProduced, String description) {
        totalChanged = null;
        coChanged = null;
        carbonChanged = null;
        lastChanged = null;
        return Communication.addAction(actionName, points, carbonReduced, carbonProduced, description);
    }

    public static ArrayList<Action> getLastThreeActions() {
        if (lastChanged == null) {
            lastChanged = Communication.getLastThreeActions();
        }
        return lastChanged;
    }

    public static int getMyTotalScore() {
        if (totalChanged == null) {
            totalChanged = Communication.getMyTotalScore();
        }
        return totalChanged;
    }

    public static User getUser() {
        if (userChanged == null) {
            userChanged = Communication.getUser();
        }
        return userChanged;
    }

    public static boolean addFriend(String friendUsername) {
        friendsChanged = null;
        return Communication.addFriend(friendUsername);
    }

    public static ArrayList<CompareFriends> getFriends() {
        if (friendsChanged == null) {
            friendsChanged = Communication.getFriends();
        }
        return friendsChanged;
    }

    public static Action carbon() {
        if (carbonChanged == null) {
            carbonChanged = Communication.carbon();
        }
        return carbonChanged;
    }


    public static double[] getRecentCOSavings() {
        if (coChanged == null) {
            coChanged = Communication.getRecentCOSavings();
        }
        return coChanged;
    }


    // ==========================  SETTERS =====================================

    public static void setUserChanged(User userChanged) {
        ComCached.userChanged = userChanged;
    }

    public static void setTotalChanged(Integer totalChanged) {
        ComCached.totalChanged = totalChanged;
    }

    public static void setLastChanged(ArrayList<Action> lastChanged) {
        ComCached.lastChanged = lastChanged;
    }

    public static void setCoChanged(double[] coChanged) {
        ComCached.coChanged = coChanged;
    }

    public static void setCarbonChanged(Action carbonChanged) {
        ComCached.carbonChanged = carbonChanged;
    }

    public static void setFriendsChanged(ArrayList<CompareFriends> friendsChanged) {
        ComCached.friendsChanged = friendsChanged;
    }
}
