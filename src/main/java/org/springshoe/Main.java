package org.springshoe;

import org.reflections.Reflections;
import org.springshoe.annotation.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/*
*
* Create a Map with every class with its dependency
*
**/
@SuppressWarnings({ "rawtypes"})
public class Main {
    static void main(){
        Reflections reflections = new Reflections("org.springshoe");
        Set<Class<?>> classesWithAnnotation = reflections.getTypesAnnotatedWith(Service.class);
        Map<Class<?>, List<Field>>  dependenciesMap = new HashMap<>();
        for(Class<?> cls: classesWithAnnotation){
            List<Field> dependencies = List.of(cls.getDeclaredFields());
            dependenciesMap.put(cls, dependencies);
        }
    }
}
