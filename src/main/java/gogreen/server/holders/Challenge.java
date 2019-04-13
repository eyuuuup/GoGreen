package gogreen.server.holders;

/**
 * This class represents a Challenge.
 */
public class Challenge {

    private String  userA;
    private String  userB;
    private int     scoreA;
    private int     scoreB;
    private int     goal;
    private int    state;
    private boolean onA;

    /**
     * Constructor for the Challenge containing the two users, their scores and the goal.
     * @param userA  String of userA
     * @param userB  String of userB
     * @param scoreA Score of userA
     * @param scoreB score of userB
     * @param goal   amount of points needed
     */
    public Challenge(String userA, String userB, int scoreA, int scoreB, int goal) {
        this.userA = userA;
        this.userB = userB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.goal = goal;
    }

    /**
     * The empty constructor for Challenge.
     */
    public Challenge() {
    }

    /**
     * Getter for the first user.
     * @return user A
     */
    public String getUserA() {
        return userA;
    }

    /**
     * Setter for the first user.
     * @param userA user A
     */
    public void setUserA(String userA) {
        this.userA = userA;
    }

    /**
     * Getter for the second user.
     * @return user B
     */
    public String getUserB() {
        return userB;
    }

    /**
     * Setter for the second user.
     * @param userB user B
     */
    public void setUserB(String userB) {
        this.userB = userB;
    }

    /**
     * Getter for the score of the first user.
     * @return the score of user A
     */
    public int getScoreA() {
        return scoreA;
    }

    /**
     * Setter for the score of the first user.
     * @param scoreA the score of user A
     */
    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    /**
     * Getter for the score of the second user.
     * @return the score of user B
     */
    public int getScoreB() {
        return scoreB;
    }

    /**
     * Setter for the score of the second user.
     * @param scoreB the score of user B
     */
    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    /**
     * Getter for the goal (in points) of the challenge.
     * @return the goal
     */
    public int getGoal() {
        return goal;
    }

    /**
     * Setter for the goal (in points) of the challenge.
     * @param goal the goal
     */
    public void setGoal(int goal) {
        this.goal = goal;
    }

    /**
     * Getter for the state of the challenge.
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * Setter for the state of the challenge.
     * @param state the state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Whether the challenge is on user A.
     * @return whether the challenge is on user A
     */
    public boolean isOnA() {
        return onA;
    }

    /**
     * Sets the challenge on user A or B.
     * @param onA whether the challenge is on user A.
     */
    public void setOnA(boolean onA) {
        this.onA = onA;
    }

}
