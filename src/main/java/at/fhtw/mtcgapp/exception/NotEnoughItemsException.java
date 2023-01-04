package at.fhtw.mtcgapp.exception;

public class NotEnoughItemsException extends RuntimeException{

    public NotEnoughItemsException(String message) {
        super(message);
    }

    public NotEnoughItemsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughItemsException(Throwable cause) { super(cause); }
}
