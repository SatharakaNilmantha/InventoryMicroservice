package com.Order.Order.Exception;



public class ServiceCommunicationException extends RuntimeException {

    public ServiceCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}