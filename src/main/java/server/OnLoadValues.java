package server;

/**
 * This class is for the on load values.
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



    public OnLoadValues() {
        user = new User();
        followers = 0;
        following = 0;
        challenges = new ChallengesList();
        solarPanel =false;
        electricCar=false;
        envGroup=false;
        carbonReduce=0;
    }

    public OnLoadValues(User user, int followers, int following, ChallengesList challenges, boolean solarCar, boolean electricCar, boolean envGroup, double carbonReduce) {
        this.user = user;
        this.followers = followers;
        this.following = following;
        this.challenges = challenges;
        this.solarPanel  = solarCar;
        this.electricCar = electricCar;
        this.envGroup = envGroup;
        this.carbonReduce=carbonReduce;
    }

    public User getUser() {
        return user;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public ChallengesList getChallenges() {
        return challenges;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setChallenges(ChallengesList challenges) {
        this.challenges = challenges;
    }


    public boolean isSolarPanel() {
        return solarPanel;
    }

    public void setSolarPanel(boolean solarPanel) {
        this.solarPanel = solarPanel;
    }

    public boolean isElectricCar() {
        return electricCar;
    }

    public void setElectricCar(boolean electricCar) {
        this.electricCar = electricCar;
    }

    public boolean isEnvGroup() {
        return envGroup;
    }

    public void setEnvGroup(boolean envGroup) {
        this.envGroup = envGroup;
    }


    public double getCarbonReduce() {
        return carbonReduce;
    }

    public void setCarbonReduce(double carbonReduce) {
        this.carbonReduce = carbonReduce;
    }


}
