package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class FavouriteGamesExistenceException extends EntityExistenceException {
    public FavouriteGamesExistenceException(String message) {
        super(message);
    }

    public FavouriteGamesExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
