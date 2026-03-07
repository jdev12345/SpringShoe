package org.springshoe.utils;

import java.lang.annotation.Annotation;

public class DependencyUtil {

    public static boolean isAnnotatedWith(Class<?> clazz, Class<? extends Annotation> target) {
        if (clazz.isAnnotationPresent(target)) {
            return true;
        }
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(target)) {
                return true;
            }
        }
        return false;
    }
}
