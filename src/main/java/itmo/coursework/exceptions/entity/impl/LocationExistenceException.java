package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class LocationExistenceException extends EntityExistenceException {
    public LocationExistenceException(String message) {
        super(message);
    }

    public LocationExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
