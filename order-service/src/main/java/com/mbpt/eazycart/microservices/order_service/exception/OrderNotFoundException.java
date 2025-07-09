package com.mbpt.eazycart.microservices.order_service.exception;

// @ResponseStatus is an alternative way to map an exception to a specific HTTP status code,
// but we'll primarily use the @ExceptionHandler for more control over the response body.
// It's still good for documentation or if you want a simpler approach for basic errors.
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}