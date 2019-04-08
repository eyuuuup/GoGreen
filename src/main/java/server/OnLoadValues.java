package server;

/**
 * This class is for the on load values.
 */
public class OnLoadValues {

    private User user;
    private FriendsList followers;
    private FriendsList following;
    private ChallengesList challenges;
    private boolean solarCar;
    private boolean electricCar;
    private boolean envGroup;



    public OnLoadValues() {
        user = new User();
        followers = new FriendsList();
        following = new FriendsList();
        challenges = new ChallengesList();
        solarCar=false;
        electricCar=false;
        envGroup=false;
    }

    public OnLoadValues(User user, FriendsList followers, FriendsList following, ChallengesList challenges, boolean solarCar, boolean electricCar, boolean envGroup) {
        this.user = user;
        this.followers = followers;
        this.following = following;
        this.challenges = challenges;
        this.solarCar = solarCar;
        this.electricCar = electricCar;
        this.envGroup = envGroup;
    }

    public User getUser() {
        return user;
    }

    public FriendsList getFollowers() {
        return followers;
    }

    public FriendsList getFollowing() {
        return following;
    }

    public ChallengesList getChallenges() {
        return challenges;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFollowers(FriendsList followers) {
        this.followers = followers;
    }

    public void setFollowing(FriendsList following) {
        this.following = following;
    }

    public void setChallenges(ChallengesList challenges) {
        this.challenges = challenges;
    }


}
