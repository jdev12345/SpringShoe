package org.springshoe.utils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public class BeanSeparator {

    public static List<Object> getBeansWithAnnotation(Map<Class<?>, Object> beanMap, Class<? extends Annotation> annotation){
        return beanMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isAnnotationPresent(annotation))
                .map(Map.Entry::getValue)
                .toList();
    }
}
