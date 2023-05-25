package com.nashss.se.beefy.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5661468145297930928L;

    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
