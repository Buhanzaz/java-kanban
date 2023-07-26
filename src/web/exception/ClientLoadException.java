package web.exception;

public class ClientLoadException extends RuntimeException {

    public ClientLoadException(String message) {
        super(message);
    }

    public ClientLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
