package Exceptions;

public class MissingSelectionException extends RuntimeException{
    public MissingSelectionException() {
    }

    public MissingSelectionException(String message) {
        super(message);
    }

    public MissingSelectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingSelectionException(Throwable cause) {
        super(cause);
    }

    public MissingSelectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
