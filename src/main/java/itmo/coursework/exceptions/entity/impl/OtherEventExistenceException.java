package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class OtherEventExistenceException extends EntityExistenceException {
    public OtherEventExistenceException(String message) {
        super(message);
    }

    public OtherEventExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
