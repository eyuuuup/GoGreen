package gogreen.server;

/**
 * This class represents an exception when the description is too long.
 */
public class DescriptionTooLong extends Exception {
    /**
     * Constructor for the DescriptionTooLong with an error message.
     * @param errorMessage the error message
     */
    public DescriptionTooLong(String errorMessage) {
        super(errorMessage);
    }
}
