package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class DistrictExistenceException extends EntityExistenceException {
    public DistrictExistenceException(String message) {
        super(message);
    }

    public DistrictExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
