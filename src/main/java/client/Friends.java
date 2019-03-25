package client;

public class Friends {
    private String token;
    private String username;
    
    public Friends(String token, String username) {
        this.token = token;
        this.username = username;
    }
    
    public Friends() {
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
