package com.mbpt.eazycart.microservices.order_service.exception;


public class InvalidOrderItemsException extends RuntimeException {
    public InvalidOrderItemsException(String message) {
        super(message);
    }
}
