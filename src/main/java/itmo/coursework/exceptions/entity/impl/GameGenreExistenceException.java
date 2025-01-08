package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class GameGenreExistenceException extends EntityExistenceException {
    public GameGenreExistenceException(String message) {
        super(message);
    }

    public GameGenreExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
