package client;

public class TokenResponse {
    private String  token;
    private boolean legit;

    public TokenResponse(String token, boolean legit) {
        this.token = token;
        this.legit = legit;
    }

    public TokenResponse() { }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLegit() {
        return legit;
    }

    public void setLegit(boolean legit) {
        this.legit = legit;
    }

}
