package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class ProfileExistenceException extends EntityExistenceException {
    public ProfileExistenceException(String message) {
        super(message);
    }

    public ProfileExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
