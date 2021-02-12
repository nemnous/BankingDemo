package com.nemnous.bank.exceptions;

/**
 * Exception when the balance is not available in account.
 * @author nemnous
 *
 */
@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {

    private final String message;

    /**
     * Constructor with message.
     * @param message
     */
    public InsufficientBalanceException(final String message) {
        this.message = message;
    }

    /**
     * Constructor with Throwable cause and message.
     * @param cause
     * @param message
     */
    public InsufficientBalanceException(final Throwable cause, final String message) {
        super(cause);
        this.message = message;
    }

    /**
     * @return message
     * returns the value of private class variable message.
     */
    @Override
    public String getMessage() {
        return message;
    }

}
