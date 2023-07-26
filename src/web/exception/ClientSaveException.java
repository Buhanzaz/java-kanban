package web.exception;

public class ClientSaveException extends RuntimeException {

    public ClientSaveException(String message) {
        super(message);
    }

    public ClientSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
