package org.springshoe.services;

import org.springshoe.annotation.bean.Service;

@Service
public class ServiceC {
    public ServiceC() {
        System.out.println("ServiceC was invoked");
    }

    public String test() {
        return "World";
    }
}
