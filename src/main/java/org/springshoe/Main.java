package org.springshoe;

import org.reflections.Reflections;
import org.springshoe.annotation.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/*
*
* GET all classes with annotation service
*
**/
@SuppressWarnings({ "rawtypes"})
public class Main {
    static void main() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("org.springshoe");
        Set<Class<?>> classesWithAnnotation = reflections.getTypesAnnotatedWith(Service.class);
        System.out.println(classesWithAnnotation);
    }
}
