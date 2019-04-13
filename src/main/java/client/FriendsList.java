package client;

import java.util.ArrayList;

/**
 * This class represents a list of friends.
 */
public class FriendsList {

    ArrayList<CompareFriends> list;

    /**
     * Constructor for FriendsList.
     * @param list the list of friends
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
     * Getter for the list of friends.
     * @return the list of friends
     */
    public ArrayList<CompareFriends> getList() {
        return list;
    }

    /**
     * Setter for the list of friends.
     * @param list the list of friends
     */
    public void setList(ArrayList<CompareFriends> list) {
        this.list = list;
    }
}
