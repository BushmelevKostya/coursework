package itmo.coursework.exceptions.entity.base;

public abstract class EntityExistenceException extends RuntimeException {
    public EntityExistenceException(String message) {
        super(message);
    }

    public EntityExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
