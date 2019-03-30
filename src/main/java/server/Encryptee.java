package server;

/**
 * This class is for getting encrypted token.
 */
public class Encryptee {

    private byte[] message;
    private String token;

    /**
     * Constructs a new Encyptee.
     * @param message message
     * @param token token
     */
    public Encryptee(byte[] message, String token) {
        this.message = message;
        this.token = token;
    }

    /**
     * getter for th message.
     * @return message
     */
    public byte[] getMessage() {
        return message;
    }

    /**
     * setter for the message.
     * @param message message
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }

    /**
     * getter for the token.
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * setter for the token.
     * @param token token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
