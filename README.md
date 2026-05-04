# SpringShoe 👟

SpringShoe is a lightweight, custom-built Dependency Injection (DI) and Web Framework for Java. This project was developed as a deep dive into the inner workings of the Spring Framework, specifically focused on IoC (Inversion of Control), dependency management, and high-performance request routing.

## 🚀 Key Features

- **Custom IoC Container**: Automated component scanning and bean lifecycle management using custom annotations (`@Controller`, `@Service`).
- **Sophisticated Dependency Management**:
    - **Topological Sorting**: Uses **Kahn’s Algorithm** to determine the correct order of bean creation.
    - **Circular Dependency Detection**: Automatically detects and throws exceptions for circular references in the dependency graph.
- **Embedded Web Server**: Integrated **Jetty** server for serving RESTful APIs.
- **High-Performance Routing**: Unlike traditional frameworks that rely purely on reflection for each request, SpringShoe uses `LambdaMetafactory` and `MethodHandles` to generate high-performance route invokers at startup.
- **JSON Support**: Native integration with Jackson for automatic response serialization.

## 🛠️ Architecture

SpringShoe follows a modern micro-framework architecture:

1.  **Scanning Phase**: Discovers classes annotated with `@Service` and `@Controller`.
2.  **Validation Phase**: Builds a directed dependency graph and validates it using topological sorting.
3.  **Injection Phase**: Instantiates beans and performs constructor-based injection.
4.  **Web Phase**: Boots an embedded Jetty server and maps HTTP routes to controller methods.

## 💻 Code Example

### 1. Define a Service
```java
@Service
public class GreetingService {
    public String getGreeting() {
        return "Hello from SpringShoe!";
    }
}
```

### 2. Define a Controller
```java
@Controller
public class MyController {
    private final GreetingService greetingService;

    public MyController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Path(route = "/hello", method = REST.GET)
    public String sayHello() {
        return greetingService.getGreeting();
    }
}
```

### 3. Run the Application
```java
public class Main {
    public static void main(String[] args) throws Throwable {
        new SpringShoe().run();
    }
}
```

## 📝 Learning Objectives

This project was built to master:
- Java Reflection and Annotation processing.
- Graph Theory applications in software engineering (Topological Sort).
- Advanced Java JVM features like `MethodHandles` and `LambdaMetafactory`.
- Servlet API and Embedded Server integration.
