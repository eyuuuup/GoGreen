package client;

import java.util.ArrayList;

/**
 * This class if for returning the list of friend objects.
 */
public class FriendsList {
    ArrayList<CompareFriends> list;

    /**
     * Constructor.
     *
     * @param list list of CompareFriends
     */
    public FriendsList(ArrayList<CompareFriends> list) {
        this.list = list;
    }

    /**
     * Empty constructor.
     */
    public FriendsList() {
        list = new ArrayList<>();
    }

    /**
     * Getter for list of friend objects.
     *
     * @return a list of friends
     */
    public ArrayList<CompareFriends> getList() {
        return list;
    }

    /**
     * Setter for the list of friends.
     *
     * @param list list of Compare friends
     */
    public void setList(ArrayList<CompareFriends> list) {
        this.list = list;
    }
}
