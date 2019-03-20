package client;

public class Encryptee {

    private byte[] message;
    private String token;

    public Encryptee(byte[] message, String token) {
        this.message = message;
        this.token = token;
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
