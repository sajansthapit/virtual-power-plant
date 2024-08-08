package com.sajansthapit.virtual_power_plant.exception_handler.exceptions;

public class InvalidPostCodeRangeException extends RuntimeException{

    public InvalidPostCodeRangeException() {
        super();
    }

    public InvalidPostCodeRangeException(String message) {
        super(message);
    }
}
