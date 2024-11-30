package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class CityExistenceException extends EntityExistenceException {
    public CityExistenceException(String message) {
        super(message);
    }

    public CityExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
