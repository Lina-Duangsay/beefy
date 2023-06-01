package com.nashss.se.beefy.exceptions;

public class InvalidAmountException extends RuntimeException{

    private static final long serialVersionUID = -8353955593237702472L;


    public InvalidAmountException(){
        super();
    }

    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(Throwable cause) {
        super(cause);
    }

    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }



}
