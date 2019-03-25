package client;

import java.util.ArrayList;

public class FriendsList {
    ArrayList<CompareFriends> list;
    
    public FriendsList(ArrayList<CompareFriends> list) {
        this.list = list;
    }
    
    public ArrayList<CompareFriends> getList() {
        return list;
    }
    
    public void setList(ArrayList<CompareFriends> list) {
        this.list = list;
    }
}
