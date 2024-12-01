package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class GameEventExistenceException extends EntityExistenceException {
    public GameEventExistenceException(String message) {
        super(message);
    }

    public GameEventExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
