package client;

public class Challenge {
    private String  userA;
    private String  userB;
    private int     scoreA;
    private int     scoreB;
    private int     goal;
    private int    state;
    private boolean onA;

    /**
     * Constructor for the Challenge class.
     * @param userA  String of userA.
     * @param userB  String of userB.
     * @param scoreA Score of userA.
     * @param scoreB score of userB.
     * @param goal   amount of points needed.
     */
    public Challenge(String userA, String userB, int scoreA, int scoreB, int goal) {
        this.userA = userA;
        this.userB = userB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.goal = goal;
    }

    public Challenge() {
    }

    public String getUserA() {
        return userA;
    }

    public void setUserA(String userA) {
        this.userA = userA;
    }

    public String getUserB() {
        return userB;
    }

    public void setUserB(String userB) {
        this.userB = userB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isOnA() {
        return onA;
    }

    public void setOnA(boolean onA) {
        this.onA = onA;
    }

}
