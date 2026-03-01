package org.springshoe.core;

import org.springshoe.annotation.bean.Controller;
import org.springshoe.annotation.bean.Service;
import org.springshoe.utils.BeanSeparator;
import org.springshoe.webserver.JettyServer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringShoe {
    private static final List<Class<? extends Annotation>> beanAnnotations = new ArrayList<>();

    static{
        beanAnnotations.add(Controller.class);
        beanAnnotations.add(Service.class);
    }

    public void init() throws Exception {
        Map<Class<?>, Object> beanMap = manageDependencyInjection();
        createWebServer(beanMap);

    }

    private void createWebServer(Map<Class<?>, Object> beanMap) throws Exception {
        JettyServer server = new JettyServer();
        List<Object> beans = BeanSeparator.getBeansWithAnnotation(beanMap, Controller.class);
        server.start(beans);
    }

    private Map<Class<?>, Object> manageDependencyInjection(){
        Map<Class<?>, List<Field>> dependenciesMap = new HashMap<>();
        for(Class<? extends Annotation> clz : beanAnnotations){
            HierarchyDependency<?> hierarchyDependency = new HierarchyDependency<>(clz);
            dependenciesMap.putAll(hierarchyDependency.populateHierarchy());
        }
        DependencyGraphValidator validator = new DependencyGraphValidator(dependenciesMap);
        List<Class<?>> topoSortAray = validator.validate();
        DependencyInjector injector = new DependencyInjector();
        return injector.getBeans(topoSortAray);
    }

}
