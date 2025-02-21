package io.github.wesleyosantos91.domain.exception;

public class CustomerHasOrdersException extends RuntimeException {
    public CustomerHasOrdersException(String message) {
        super(message);
    }
}