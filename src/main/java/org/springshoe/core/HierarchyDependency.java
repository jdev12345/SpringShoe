package org.springshoe.core;

import org.reflections.Reflections;
import org.springshoe.utils.Constaints;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HierarchyDependency<T extends Annotation> {
    private static final Reflections reflections = new Reflections(Constaints.root);
    private final Class<T> annotationClass;

    public HierarchyDependency(@Nonnull Class<T> annotationClass){
        this.annotationClass = annotationClass;
    }

    public void populateHierarchy(@Nonnull Map<Class<?>, List<Field>> dependenciesMap){
        Set<Class<?>> classesWithAnnotation = getClassWithAnnotation();
        for(Class<?> cls: classesWithAnnotation){
            List<Field> dependencies = List.of(cls.getDeclaredFields());
            dependenciesMap.put(cls, dependencies);
        }
    }

    public Map<Class<?>, List<Field>> populateHierarchy(){
        Map<Class<?>, List<Field>> dependenciesMap = new HashMap<>();
        populateHierarchy(dependenciesMap);
        return dependenciesMap;
    }

    private Set<Class<?>> getClassWithAnnotation(){
        return reflections.getTypesAnnotatedWith(annotationClass);
    }
}
