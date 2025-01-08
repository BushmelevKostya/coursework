package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class GameHistoryExistenceException extends EntityExistenceException {
    public GameHistoryExistenceException(String message) {
        super(message);
    }

    public GameHistoryExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
