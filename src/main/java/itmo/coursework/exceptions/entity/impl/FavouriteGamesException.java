package itmo.coursework.exceptions.entity.impl;

import itmo.coursework.exceptions.entity.base.EntityExistenceException;

public class FavouriteGamesException extends EntityExistenceException {
    public FavouriteGamesException(String message) {
        super(message);
    }

    public FavouriteGamesException(String message, Throwable cause) {
        super(message, cause);
    }
}
