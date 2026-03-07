package org.springshoe.core;

import org.reflections.Reflections;
import org.springshoe.annotation.BaseBeanClass;
import org.springshoe.utils.Constants;
import org.springshoe.utils.DependencyUtil;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class HierarchyDependency<T extends Annotation> {
    private static final Reflections reflections = new Reflections(Constants.ROOT_PACKAGE);
    private final Class<T> annotationClass;

    public HierarchyDependency(@Nonnull Class<T> annotationClass){
        this.annotationClass = annotationClass;
    }

    public void populateHierarchy(@Nonnull Map<Class<?>, List<Field>> dependenciesMap){
        Set<Class<?>> classesWithAnnotation = getClassWithAnnotation();
        for(Class<?> cls: classesWithAnnotation){
            dependenciesMap.put(cls, getAnnotatedDependencies(cls));
        }
    }

    public List<Field> getAnnotatedDependencies(Class<?> cls){
        List<Field> dependencies = List.of(cls.getDeclaredFields());
        List<Field> annotatedDependencies = new ArrayList<>();
        for(Field field : dependencies){
            if(DependencyUtil.isAnnotatedWith(field.getType(), BaseBeanClass.class)){
                annotatedDependencies.add(field);
            }
        }
        return annotatedDependencies;
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
