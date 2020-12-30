package cz.cvut.bikesharingproject.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException create(String resource, Object identifier) {
        return new NotFoundException(resource + " identified by " + identifier + " is not found.");
    }
}
