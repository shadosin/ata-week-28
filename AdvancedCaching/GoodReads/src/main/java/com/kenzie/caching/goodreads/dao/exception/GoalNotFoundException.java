package com.kenzie.caching.goodreads.dao.exception;

/**
 * Exception used when a requested reading goal cannot be found.
 */
public class GoalNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -273093166228140635L;

    /**
     * Constructs a GoalNotFoundException with the provided message.
     * @param message detailed message about the exception
     */
    public GoalNotFoundException(final String message) {
        super(message);
    }
}
