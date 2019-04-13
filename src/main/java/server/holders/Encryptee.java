package server.holders;

/**
 * This class represents the encrypted token.
 */
public class Encryptee {

    private byte[] message;
    private String token;

    /**
     * Constructs a new Encyptee with a message and the user's token.
     * @param message the message
     * @param token   the user's token
     */
    public Encryptee(byte[] message, String token) {
        this.message = message;
        this.token = token;
    }

    /**
     * Getter for the message.
     * @return the message
     */
    public byte[] getMessage() {
        return message;
    }

    /**
     * Setter for the message.
     * @param message the message
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }

    /**
     * Getter for the user's token.
     * @return the token of the user
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
}
