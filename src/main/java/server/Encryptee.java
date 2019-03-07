package server;

public class Encryptee {

    private byte[] message;
    private String token;

    public Encryptee(byte[] message, String userID) {
        this.message = message;
        this.token = userID;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
