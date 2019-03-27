package client;

import java.util.ArrayList;

/**
 * for the list of freinds
 */
public class FriendsList {
    ArrayList<CompareFriends> list;

    public FriendsList(ArrayList<CompareFriends> list) {
        this.list = list;
    }

    public FriendsList() {
        list = new ArrayList<>();
    }

    public ArrayList<CompareFriends> getList() {
        return list;
    }

    public void setList(ArrayList<CompareFriends> list) {
        this.list = list;
    }
}
