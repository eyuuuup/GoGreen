package client;

/**
 * This class is for sending user's token.
 */
public class TokenResponse {
    private String token;
    private boolean legit;

    /**
     * Empty constructor.
     */
    public TokenResponse() {
    }

    /**
     * Constructor.
     *
     * @param token
     * @param legit
     */
    public TokenResponse(String token, boolean legit) {
        this.token = token;
        this.legit = legit;
    }

    /**
     * Getter for token.
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for token.
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for legit.
     *
     * @return if the token passd is correct or not
     */
    public boolean isLegit() {
        return legit;
    }

    /**
     * Setter for legit.
     *
     * @param legit set the legit correct or not
     */
    public void setLegit(boolean legit) {
        this.legit = legit;
    }

}
