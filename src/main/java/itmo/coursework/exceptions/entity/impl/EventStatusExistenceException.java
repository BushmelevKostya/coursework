package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class EventStatusExistenceException extends EntityExistenceException {
    public EventStatusExistenceException(String message) {
        super(message);
    }

    public EventStatusExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
