package gogreen.server;

public class DescriptionTooLong extends Exception {
    public DescriptionTooLong(String errorMessage) {
        super(errorMessage);
    }
}
