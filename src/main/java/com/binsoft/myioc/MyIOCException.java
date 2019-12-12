package com.binsoft.myioc;

/**
 * MyIOCException
 */
public class MyIOCException extends RuntimeException {
    public MyIOCException(final Throwable t) {
        super(t);
    }

    public MyIOCException(final String message) {
        super(message);
    }

    public MyIOCException(final String message, final Throwable t) {
        super(message, t);
    }

}
