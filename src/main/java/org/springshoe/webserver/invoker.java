package org.springshoe.webserver;

import org.springshoe.annotation.RouteInvoker;

record invoker(Object controllerInstance, RouteInvoker method) {
}
