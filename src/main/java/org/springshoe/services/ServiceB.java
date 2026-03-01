package org.springshoe.services;

import org.springshoe.annotation.bean.Service;

@Service
public class ServiceB {

    public ServiceB() {
        System.out.println("ServiceB was invoked");
    }

    public String test() {
        return "Hello";
    }
}
