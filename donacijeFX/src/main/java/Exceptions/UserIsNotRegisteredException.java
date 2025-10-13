package Exceptions;

public class UserIsNotRegisteredException extends Exception {

    public UserIsNotRegisteredException() {
    }

    public UserIsNotRegisteredException(String message) {
        super(message);
    }

    public UserIsNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
