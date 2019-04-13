package server;

/**
 * This class represents a collection of user information
 * which should be loaded when the application starts.
 */
public class OnLoadValues {

    private User user;
    private int followers;
    private int following;
    private ChallengesList challenges;
    private boolean solarPanel;
    private boolean electricCar;
    private boolean envGroup;
    private double carbonReduce;

    /**
     * Constructor of OnLoadValues which sets the user, the amount of followers,
     * the amount of people the user follows, the user's challenges,
     * whether the user has solarPanels, whether the user has an electric car,
     * whether the user has joined an env Group and the amount of CO2 the user has reduced
     * to default values.
     */
    public OnLoadValues() {
        user = new User();
        followers = 0;
        following = 0;
        challenges = new ChallengesList();
        solarPanel = false;
        electricCar = false;
        envGroup = false;
        carbonReduce = 0;
    }

    /**
     * Constructor of OnLoadValues which sets the user, the amount of followers,
     * the amount of people the user follows, the user's challenges,
     * whether the user has solarPanels, whether the user has an electric car,
     * whether the user has joined an env Group and the amount of CO2 the user has reduced
     * to given values.
     * @param user         the user
     * @param followers    the amount of followers
     * @param following    the amount of people the user follows
     * @param challenges   the user's challenges
     * @param solarPanel   whether the user has solar
     * @param electricCar  whether the user has an electric car
     * @param envGroup     whether the user has joined a env Group
     * @param carbonReduce the amount of CO2 the user has reduced
     */
    public OnLoadValues(User user, int followers, int following,
                        ChallengesList challenges, boolean solarPanel,
                        boolean electricCar, boolean envGroup, double carbonReduce) {
        this.user = user;
        this.followers = followers;
        this.following = following;
        this.challenges = challenges;
        this.solarPanel = solarPanel;
        this.electricCar = electricCar;
        this.envGroup = envGroup;
        this.carbonReduce = carbonReduce;
    }

    /**
     * Getter for the user.
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for the amount of followers of the user.
     * @return the amount of followers of the user
     */
    public int getFollowers() {
        return followers;
    }

    /**
     * Getter for the amount of people the user follows.
     * @return the amount of people the user follows
     */
    public int getFollowing() {
        return following;
    }

    /**
     * Getter for the list of the user's challenges.
     * @return the list of the user's challenges
     */
    public ChallengesList getChallenges() {
        return challenges;
    }

    /**
     * Setter for the user.
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter for the amount of followers of the user.
     * @param followers the amount of followers of the user
     */
    public void setFollowers(int followers) {
        this.followers = followers;
    }

    /**
     * Setter for the amount of people the user follows.
     * @param following the amount of people the user follows
     */
    public void setFollowing(int following) {
        this.following = following;
    }

    /**
     * Setter for the list of the user's challenges.
     * @param challenges the list of the user's challenges
     */
    public void setChallenges(ChallengesList challenges) {
        this.challenges = challenges;
    }

    /**
     * Getter whether the user has solar panels.
     * @return whether the user has solar panels
     */
    public boolean isSolarPanel() {
        return solarPanel;
    }

    /**
     * Setter whether the user has solar panels.
     * @param solarPanel whether the user has solar panels
     */
    public void setSolarPanel(boolean solarPanel) {
        this.solarPanel = solarPanel;
    }

    /**
     * Getter whether the user has an electric car.
     * @return whether the user has an electric car
     */
    public boolean isElectricCar() {
        return electricCar;
    }

    /**
     * Setter whether the user has an electric car.
     * @param electricCar whether the user has an electric car
     */
    public void setElectricCar(boolean electricCar) {
        this.electricCar = electricCar;
    }

    /**
     * Getter whether the user has joined an env Group.
     * @return whether the user has joined an env Group
     */
    public boolean isEnvGroup() {
        return envGroup;
    }

    /**
     * Setter whether the user has joined an env Group.
     * @param envGroup whether the user has joined an env Group
     */
    public void setEnvGroup(boolean envGroup) {
        this.envGroup = envGroup;
    }

    /**
     * Getter for the amount of CO2 the user has reduced.
     * @return the amount of CO2 the user has reduced
     */
    public double getCarbonReduce() {
        return carbonReduce;
    }

    /**
     * Setter for the amount of CO2 the user has reduced.
     * @param carbonReduce the amount of CO2 the user has reduced
     */
    public void setCarbonReduce(double carbonReduce) {
        this.carbonReduce = carbonReduce;
    }
}
