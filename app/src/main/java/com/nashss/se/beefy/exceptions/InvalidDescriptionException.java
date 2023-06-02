package com.nashss.se.beefy.exceptions;

public class InvalidDescriptionException extends RuntimeException{
    private static final long serialVersionUID = -6697255291254446990L;

    public InvalidDescriptionException(){
        super();
    }

    public InvalidDescriptionException(String message) {
        super(message);
    }

    public InvalidDescriptionException(Throwable cause) {
        super(cause);
    }

    public InvalidDescriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
