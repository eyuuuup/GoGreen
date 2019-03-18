package server;

public class CompareFriends {
    private String usrname;
    private int score;

    public CompareFriends(String usrname, int score) {
        this.usrname = usrname;
        this.score = score;
    }
    public CompareFriends() {
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
