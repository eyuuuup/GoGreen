package server;

public class TokenResponse {
    private String token;
    private boolean bool;

    public TokenResponse(String token, boolean bool)
    {
        this.token=token;
        this.bool=bool;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
