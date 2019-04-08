package server;

/**
 * This class is for the on load values.
 */
public class OnLoadValues {
    /**
     * solar panels
     * electric car
     * envoirment group
     */
    private User user;
    private FriendsList followers;
    private FriendsList following;
    private ChallengesList challenges;

    public OnLoadValues(User user, FriendsList followers, FriendsList following, ChallengesList challenges) {
        this.user = user;
        this.followers = followers;
        this.following = following;
        this.challenges = challenges;
    }

    public OnLoadValues() {
        user = new User();
        followers = new FriendsList();
        following = new FriendsList();
        challenges = new ChallengesList();
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
