package org.springshoe.exception;

public class CircularDependencyException extends RuntimeException {
    public CircularDependencyException() {
        super("Circular dependency Exception caught in your program");
    }
}
