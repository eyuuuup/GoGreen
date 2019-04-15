package gogreen.server.holders;

/**
 * This class represents the user's token for transactions.
 */
public class TokenResponse {

    private String token;
    private boolean legit;

    /**
     * Empty constructor for TokenResponse.
     */
    public TokenResponse() {
    }

    /**
     * Constructor for TokenResponse with a token and whether the token is legit.
     * @param token String token of user
     * @param legit if token is correct or not
     */
    public TokenResponse(String token, boolean legit) {
        this.token = token;
        this.legit = legit;
    }

    /**
     * Getter for the user's token.
     * @return the user's token
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for the user's token.
     * @param token the user's token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter whether the token is legit.
     * @return whether the token is legit
     */
    public boolean isLegit() {
        return legit;
    }

    /**
     * Setter whether the token is legit.
     * @param legit whether the token is legit
     */
    public void setLegit(boolean legit) {
        this.legit = legit;
    }
}
