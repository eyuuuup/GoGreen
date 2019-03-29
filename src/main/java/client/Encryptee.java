package client;

/**
 * This class is for getting encrypted token
 */
public class Encryptee {

    private byte[] message;
    private String token;

    /**
     * Constructs a new Encyptee
     *
     * @param message
     * @param token
     */
    public Encryptee(byte[] message, String token) {
        this.message = message;
        this.token = token;
    }

    /**
     * getter for th message
     *
     * @return
     */
    public byte[] getMessage() {
        return message;
    }

    /**
     * setter for the message
     *
     * @param message
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }

    /**
     * getter for the token
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * setter for the token
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}

