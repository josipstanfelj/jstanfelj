package Exceptions;

public class UserIsRegisteredException extends Exception {

    public UserIsRegisteredException() {
    }

    public UserIsRegisteredException(String message) {
        super(message);
    }

    public UserIsRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsRegisteredException(Throwable cause) {
        super(cause);
    }
}
