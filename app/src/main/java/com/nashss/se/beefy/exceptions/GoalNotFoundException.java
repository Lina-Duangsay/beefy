package com.nashss.se.beefy.exceptions;

public class GoalNotFoundException extends RuntimeException{


    private static final long serialVersionUID = -4400319977015255502L;

    public GoalNotFoundException(){
        super();
    }

    public GoalNotFoundException(String message) {
        super(message);
    }

    public GoalNotFoundException(Throwable cause) {
        super(cause);
    }

    public GoalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
