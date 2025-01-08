package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class GenreExistenceException extends EntityExistenceException {
    public GenreExistenceException(String message) {
        super(message);
    }

    public GenreExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
