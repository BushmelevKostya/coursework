package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class GameExistenceException extends EntityExistenceException {
    public GameExistenceException(String message) {
        super(message);
    }

    public GameExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
