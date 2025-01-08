package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class GameEventProfilesException extends EntityExistenceException {
    public GameEventProfilesException(String message) {
        super(message);
    }

    public GameEventProfilesException(String message, Throwable cause) {
        super(message, cause);
    }
}
