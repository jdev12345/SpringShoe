package org.springshoe.controller;

import org.springshoe.annotation.Path;
import org.springshoe.annotation.bean.Controller;
import org.springshoe.enums.REST;
import org.springshoe.services.ServiceA;

@Controller
public class ControllerD {

    private final ServiceA serviceA;

    public ControllerD(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    @Path(route = "/helloWorld", method = REST.GET)
    public String test(){
        return serviceA.test();
    }
}
