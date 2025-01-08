package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class OtherEventProfilesExistenceException extends EntityExistenceException {
    public OtherEventProfilesExistenceException(String message) {
        super(message);
    }

    public OtherEventProfilesExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
