package org.springshoe.webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springshoe.annotation.Path;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JettyServer {
    private static Class<? extends Annotation> PATH_ANNOTATION = Path.class;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, RouteInfo> routeRegistry = new HashMap<>();

    record RouteInfo(Object controllerInstance, Method method) {}

    public void start(List<Object> controllers) throws Exception {
        Server server = new Server(8080);

        for(Object controller : controllers){
            Class<?> clazz = controller.getClass();

            for(Method method : clazz.getDeclaredMethods()){
                if(method.isAnnotationPresent(PATH_ANNOTATION)){
                    Path annotation = (Path) method.getAnnotation(PATH_ANNOTATION);
                    routeRegistry.put(annotation.method() + ":" + annotation.route(),new RouteInfo(controller, method));
                }
            }
        }

        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest,
                               HttpServletRequest request, HttpServletResponse response)
                    throws IOException {

                String httpMethod = request.getMethod();
                String path = target;
                String lookupKey = httpMethod + ":" + path;

                RouteInfo targetInfo = routeRegistry.get(lookupKey);

                if (targetInfo != null) {
                    try {
                        Object data = targetInfo.method().invoke(targetInfo.controllerInstance());

                        if (data != null) {
                            writeResponse(response, data);
                        } else {
                            response.setStatus(204);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendError(500, "Internal Server Error");
                    }
                } else {
                    response.setStatus(404);
                    response.getWriter().println("Not Found");
                }

                baseRequest.setHandled(true);
            }
        });

        server.start();
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
