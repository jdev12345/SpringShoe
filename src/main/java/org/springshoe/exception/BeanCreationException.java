package org.springshoe.exception;

public class BeanCreationException extends RuntimeException {
    public BeanCreationException(Throwable message) {
        super(message);
    }

    public BeanCreationException(String message) {
        super(message);
    }
}
