package org.springshoe.webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springshoe.annotation.Path;
import org.springshoe.annotation.RouteInvoker;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JettyServer {
    private static final Class<? extends Annotation> PATH_ANNOTATION = Path.class;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, RouteInfo> routeRegistry = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    record RouteInfo(Object controllerInstance, RouteInvoker invoker) {}

    public void start(List<Object> controllers, long startTime) throws Throwable {
        Server server = new Server(8080);
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        for(Object controller : controllers){
            Class<?> clazz = controller.getClass();

            for(Method method : clazz.getDeclaredMethods()){
                if(method.isAnnotationPresent(PATH_ANNOTATION)){
                    Path annotation = (Path) method.getAnnotation(PATH_ANNOTATION);

                    MethodHandle handle = lookup
                            .unreflect(method);

                    CallSite callSite = LambdaMetafactory.metafactory(
                            lookup,
                            "invoke",
                            MethodType.methodType(RouteInvoker.class),
                            MethodType.methodType(Object.class, Object.class),
                            handle,
                            handle.type()
                    );
                    RouteInvoker invoker =
                            (RouteInvoker) callSite.getTarget().invokeExact();
                    routeRegistry.put(annotation.method() + ":" + annotation.route(),new RouteInfo(controller, invoker));
                }
            }
        }

        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest,
                               HttpServletRequest request, HttpServletResponse response)
                    throws IOException {

                String httpMethod = request.getMethod();
                String lookupKey = httpMethod + ":" + target;

                RouteInfo targetInfo = routeRegistry.get(lookupKey);

                if (targetInfo != null) {
                    try {
                        Object result = targetInfo.invoker().invoke(targetInfo.controllerInstance());

                        if (result != null) {
                            writeResponse(response, result);
                        } else {
                            response.setStatus(204);
                        }

                    } catch (Throwable e) {
                        logger.error("Unexpected Error occurred",e);
                        response.sendError(500, "Internal Server Error");                    }
                } else {
                    response.setStatus(404);
                    response.getWriter().println("Not Found");
                }

                baseRequest.setHandled(true);
            }
        });
        server.start();
        long endTime = System.currentTimeMillis();
        logger.info("Server started on port : 8080 in {} ms", (endTime - startTime));
        server.join();
    }

    private void writeResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);

        String jsonString = objectMapper.writeValueAsString(data);

        response.getWriter().write(jsonString);
    }
}
