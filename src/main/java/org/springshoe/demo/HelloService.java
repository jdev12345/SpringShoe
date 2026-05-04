package org.springshoe.demo;

import org.springshoe.annotation.bean.Service;

@Service
public class HelloService {
    public String getGreeting(String name) {
        return "Hello, " + name + "! Welcome to SpringShoe.";
    }

    public String getStatus() {
        return "SpringShoe is running smoothly with custom Dependency Injection!";
    }
}
