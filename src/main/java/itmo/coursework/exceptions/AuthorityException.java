package itmo.coursework.exceptions;

public class AuthorityException extends RuntimeException {
    public AuthorityException(String message) {
        super(message);
    }

    public AuthorityException(String message, Throwable cause) {
        super(message, cause);
    }
}
