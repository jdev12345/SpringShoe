package org.springshoe.annotation;

@FunctionalInterface
public interface RouteInvoker {
    Object invoke(Object controller) throws Throwable;
}