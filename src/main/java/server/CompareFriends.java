package server;

public class CompareFriends {
    private String username;
    private int score;

    public CompareFriends(String username, int score) {
        this.username = username;
        this.score = score;
    }
    public CompareFriends() {
    }

    public String getUsrname() {
        return username;
    }

    public void setUsrname(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
