package org.springshoe.demo;

import org.springshoe.annotation.Path;
import org.springshoe.annotation.bean.Controller;
import org.springshoe.enums.REST;

import java.util.Map;

@Controller
public class HelloController {

    private final HelloService helloService;

    // Constructor injection!
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @Path(route = "/hello", method = REST.GET)
    public Map<String, String> sayHello() {
        return Map.of("message", helloService.getGreeting("Developer"));
    }

    @Path(route = "/status", method = REST.GET)
    public Map<String, String> checkStatus() {
        return Map.of(
            "status", "UP",
            "details", helloService.getStatus()
        );
    }
}
